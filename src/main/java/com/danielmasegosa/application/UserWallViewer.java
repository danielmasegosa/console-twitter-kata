package com.danielmasegosa.application;

import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.domain.repository.UserRepository;

import java.util.List;

public final class UserWallViewer {

    private UserRepository userRepository;

    public UserWallViewer(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Post> execute(final User user) {
        return userRepository.retrieveUserWall(user);
    }
}
