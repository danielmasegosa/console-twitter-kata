package com.danielmasegosa.application;

import com.danielmasegosa.domain.User;
import com.danielmasegosa.domain.exceptions.UserNotFound;
import com.danielmasegosa.domain.repository.UserRepository;

public class UserSubscriber {

    private UserRepository userRepository;

    public UserSubscriber(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(final User follower, final User followee) throws UserNotFound {
        userRepository.saveSubscription(follower, followee);
    }
}
