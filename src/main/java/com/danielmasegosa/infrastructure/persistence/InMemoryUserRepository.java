package com.danielmasegosa.infrastructure.persistence;

import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.domain.repository.UserRepository;
import com.danielmasegosa.infrastructure.persistence.document.PostDocument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class InMemoryUserRepository implements UserRepository {

    private InMemoryRepository inMemoryRepository;

    public InMemoryUserRepository(final InMemoryRepository inMemoryRepository) {
        this.inMemoryRepository = inMemoryRepository;
    }

    @Override
    public void savePost(final Post post) {
        inMemoryRepository.savePost(PostDocument.fromDomain(post));
    }

    @Override
    public List<Post> retrieveMessageByUser(final User user) {
        return inMemoryRepository.findPostsByUserName(user.getUserName())
                .stream()
                .map(PostDocument::toDomain)
                .sorted(Comparator.comparing(Post::getCreationDate))
                .collect(Collectors.toList());
    }

    @Override
    public void saveSubscription(final User follower, User followee) {
        inMemoryRepository.saveSubscription(follower.getUserName(), followee.getUserName());
    }

    @Override
    public List<Post> retrieveUserWall(final User user) {
        return inMemoryRepository.findByUserName(user.getUserName())
            .map(userDocument -> {
                var userPosts = new ArrayList<>(userDocument.getPosts());
                userDocument.getSubscribedTo()
                    .forEach(followeeName -> userPosts.addAll(inMemoryRepository.findPostsByUserName(followeeName)));
                return userPosts.stream().map(PostDocument::toDomain).collect(Collectors.toList());
            })
            .orElse(Collections.emptyList());
    }
}
