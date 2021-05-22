package com.danielmasegosa.application;

import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.domain.UserRepository;

import java.util.Set;

public final class MessagesRetriever {

    private UserRepository userRepository;

    public MessagesRetriever(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Set<Post> execute(final User user) {
        return userRepository.retrieveMessageByUser(user);
    }
}
