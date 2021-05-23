package com.danielmasegosa.infrastructure.terminal.commands;

import com.danielmasegosa.application.PostCreator;
import com.danielmasegosa.infrastructure.terminal.commands.dto.PostMessageDto;

public class PostMessageTerminalCommand implements TerminalCommand {

    private final PostMessageDto command;
    private final PostCreator postCreator;

    public PostMessageTerminalCommand(final PostMessageDto dto, final PostCreator postCreator) {
        this.command = dto;
        this.postCreator = postCreator;
    }

    @Override
    public void execute() {
        postCreator.execute(command.toDomain());
    }
}
