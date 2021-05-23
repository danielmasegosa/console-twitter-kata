package com.danielmasegosa.application;

import com.danielmasegosa.application.commands.PostCommand;
import com.danielmasegosa.domain.Clock;
import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.domain.repository.UserRepository;

public class PostMessageCreator {

    private UserRepository userRepository;
    private Clock clock;

    public PostMessageCreator(final UserRepository userRepository, final Clock clock) {
        this.userRepository = userRepository;
        this.clock = clock;
    }

    public void execute(final PostCommand command) {
        userRepository.savePost(new Post(new User(command.getUserName()), command.getMessage(), clock.now()));
    }
}
