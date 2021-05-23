package com.danielmasegosa.infrastructure.console.input;

import com.danielmasegosa.application.PostCreator;
import com.danielmasegosa.infrastructure.console.input.dto.PostMessageDto;

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
