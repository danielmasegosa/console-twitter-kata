package com.danielmasegosa.infrastructure.terminal.commands;

import com.danielmasegosa.application.PostCreator;
import com.danielmasegosa.infrastructure.terminal.Terminal;
import com.danielmasegosa.infrastructure.terminal.commands.dto.PostMessageDto;
import com.danielmasegosa.infrastructure.terminal.commands.dto.validation.Notification;

public class PostMessageTerminalCommand implements TerminalCommand {

    private final PostMessageDto command;
    private final PostCreator postCreator;
    private Terminal terminal;

    public PostMessageTerminalCommand(final PostMessageDto dto, final PostCreator postCreator, final Terminal terminal) {
        this.command = dto;
        this.postCreator = postCreator;
        this.terminal = terminal;
    }

    @Override
    public void execute() {
        final Notification check = command.check();
        if (check.hasErrors()){
            terminal.writeErrors(check.getErrors());
        } else {
            postCreator.execute(command.toDomain());
        }
    }
}
