package com.danielmasegosa.application;

import com.danielmasegosa.application.commands.PostCommand;
import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.domain.repository.UserRepository;
import com.danielmasegosa.domain.time.Clock;

public class PostCreator {

    private UserRepository userRepository;
    private Clock clock;

    public PostCreator(final UserRepository userRepository, final Clock clock) {
        this.userRepository = userRepository;
        this.clock = clock;
    }

    public void execute(final PostCommand command) {
        userRepository.savePost(new Post(new User(command.getUserName()), command.getMessage(), clock.now()));
    }
}
