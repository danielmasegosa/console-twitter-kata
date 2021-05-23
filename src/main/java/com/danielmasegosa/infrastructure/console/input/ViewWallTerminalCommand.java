package com.danielmasegosa.infrastructure.console.input;

import com.danielmasegosa.application.UserWallRetriever;
import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.infrastructure.console.input.dto.RequestWallDto;

import java.util.List;

public class ViewWallTerminalCommand implements TerminalCommand {

    private final RequestWallDto requestWallDto;
    private final UserWallRetriever userWallRetriever;

    public ViewWallTerminalCommand(final RequestWallDto requestWallDto, final UserWallRetriever userWallRetriever) {
        this.requestWallDto = requestWallDto;
        this.userWallRetriever = userWallRetriever;
    }

    @Override
    public void execute() {
        final List<Post> execute = userWallRetriever.execute(new User(requestWallDto.getUserName()));
    }
}
