package com.danielmasegosa.infrastructure.persistence;


import com.danielmasegosa.domain.User;
import com.danielmasegosa.domain.exceptions.UserNotFound;
import com.danielmasegosa.infrastructure.persistence.document.PostDocument;
import com.danielmasegosa.infrastructure.persistence.document.UserDocument;

import java.util.*;

public class InMemoryRepository {

    private final Map<String, UserDocument> users = new HashMap<>();

    public Optional<UserDocument> findByUserName(final String user) throws UserNotFound {
        final Optional<UserDocument> maybeUser = Optional.ofNullable(users.get(user));
        if(maybeUser.isEmpty()){
            throw new UserNotFound(user);
        }
        return maybeUser;
    }

    public void savePost(User user, final PostDocument post) {
        Optional<UserDocument> maybeUser = Optional.ofNullable(users.get(user.getUserName()));
        if (maybeUser.isPresent()) {
            updateUserAndSave(post, maybeUser.get());
        } else {
            UserDocument updatedUser = new UserDocument(user.getUserName(), List.of(post), Collections.emptySet());
            users.put(user.getUserName(), updatedUser);
        }
    }

    public void saveSubscription(final String subscriberName, String followeeName) throws UserNotFound{
        Optional.ofNullable(users.get(subscriberName))
            .ifPresentOrElse(
                userDocument -> findByUserName(followeeName)
                    .ifPresentOrElse(
                        followee -> {
                            final Set<String> subscribedTo = new HashSet<>(userDocument.getSubscribedTo());
                            subscribedTo.add(followee.getUserName());
                            final UserDocument updatedUser = userDocument.withSubscribers(subscribedTo);
                            users.put(userDocument.getUserName(), updatedUser);
                        },
                        () -> {throw new UserNotFound(subscriberName);}
                    ),
                () -> {throw new UserNotFound(subscriberName);}
            );
    }

    public List<PostDocument> findPostsByUserName(final String userName) throws UserNotFound{
        return Optional.ofNullable(users.get(userName))
                .map(UserDocument::getPosts)
                .orElseThrow(() -> {
                    throw new UserNotFound(userName);
                });
    }

    public void clear() {
        users.clear();
    }

    private void updateUserAndSave(final PostDocument post, final UserDocument userDocument) {
        List<PostDocument> posts = new ArrayList<>(userDocument.getPosts());
        posts.add(post);
        UserDocument updatedUser = new UserDocument(userDocument.getUserName(), posts, userDocument.getSubscribedTo());
        users.put(updatedUser.getUserName(), updatedUser);
    }
}
