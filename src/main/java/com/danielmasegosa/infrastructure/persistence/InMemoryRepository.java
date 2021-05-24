package com.danielmasegosa.infrastructure.persistence;


import com.danielmasegosa.infrastructure.persistence.document.PostDocument;
import com.danielmasegosa.infrastructure.persistence.document.UserDocument;

import java.util.*;

public class InMemoryRepository {

    private final Map<String, UserDocument> users = new HashMap<>();

    public Optional<UserDocument> findByUserName(final String user) {
        return Optional.ofNullable(users.get(user));
    }

    public void savePost(final PostDocument post) {
        Optional<UserDocument> maybeUser = Optional.ofNullable(users.get(post.getUserName()));
        if (maybeUser.isPresent()) {
            updateUserAndSave(post, maybeUser.get());
        } else {
            UserDocument updatedUser = new UserDocument(post.getUserName(), List.of(post), Collections.emptySet());
            users.put(post.getUserName(), updatedUser);
        }
    }

    public void saveSubscription(final String subscriber, String followee) {
        Optional.of(users.get(subscriber))
            .ifPresent(userDocument -> findByUserName(followee)
                .ifPresent(followeeDocument -> {
                    final Set<String> subscribedTo = new HashSet<>(userDocument.getSubscribedTo());
                    subscribedTo.add(followee);
                    final UserDocument updatedUser = userDocument.withSubscribers(subscribedTo);
                    users.put(subscriber, updatedUser);
                })
            );

    }

    public List<PostDocument> findPostsByUserName(final String userName) {
        return Optional.ofNullable(users.get(userName)).map(UserDocument::getPosts).orElse(List.of());
    }

    public Set<UserDocument> findSubscriptionsByUser(final String userName) {
        throw new UnsupportedOperationException("Not implemented!");
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
