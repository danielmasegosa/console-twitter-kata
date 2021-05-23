package com.danielmasegosa.infrastructure.terminal.commands;

import com.danielmasegosa.application.UserWallRetriever;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.infrastructure.terminal.Terminal;
import com.danielmasegosa.infrastructure.terminal.commands.dto.RequestWallDto;

public class ViewWallTerminalCommand implements TerminalCommand {

    private final RequestWallDto requestWallDto;
    private final UserWallRetriever userWallRetriever;
    private Terminal terminal;

    public ViewWallTerminalCommand(final RequestWallDto requestWallDto, final UserWallRetriever userWallRetriever, final Terminal terminal) {
        this.requestWallDto = requestWallDto;
        this.userWallRetriever = userWallRetriever;
        this.terminal = terminal;
    }

    @Override
    public void execute() {
        terminal.write(userWallRetriever.execute(new User(requestWallDto.getUserName())));
    }
}
