package com.danielmasegosa.application;

import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.domain.repository.UserRepository;

import java.util.List;

public class MessagesRetriever {

    private UserRepository userRepository;

    public MessagesRetriever(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Post> execute(final User user) {
        return userRepository.retrieveMessageByUser(user);
    }
}
