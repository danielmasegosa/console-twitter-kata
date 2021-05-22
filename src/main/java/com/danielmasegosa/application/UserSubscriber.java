package com.danielmasegosa.application;

import com.danielmasegosa.domain.User;
import com.danielmasegosa.domain.UserRepository;

public final class UserSubscriber {

    private UserRepository userRepository;

    public UserSubscriber(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(final User follower, final User followee) {
        userRepository.subscribeToUser(follower, followee);
    }
}
