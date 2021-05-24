package com.danielmasegosa.application;

import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.domain.exceptions.UserNotFound;
import com.danielmasegosa.domain.repository.UserRepository;

import java.util.List;

public class UserWallRetriever {

    private UserRepository userRepository;

    public UserWallRetriever(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Post> execute(final User user) throws UserNotFound {
        return userRepository.retrieveUserWall(user);
    }
}
