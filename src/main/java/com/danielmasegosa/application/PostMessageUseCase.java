package com.danielmasegosa.application;

import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.UserRepository;

public final class PostMessageUseCase {

    private UserRepository userRepository;

    public PostMessageUseCase(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(final Post post) {
        userRepository.save(post);
    }
}
