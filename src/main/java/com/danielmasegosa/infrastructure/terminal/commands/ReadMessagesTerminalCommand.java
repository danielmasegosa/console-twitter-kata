package com.danielmasegosa.infrastructure.terminal.commands;

import com.danielmasegosa.application.MessagesRetriever;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.domain.exceptions.UserNotFound;
import com.danielmasegosa.infrastructure.terminal.Terminal;
import com.danielmasegosa.infrastructure.terminal.commands.dto.RetrieveMessagesDto;

public class ReadMessagesTerminalCommand implements TerminalCommand {
    private final RetrieveMessagesDto retrieveMessagesDto;
    private final MessagesRetriever messagesRetriever;
    private Terminal terminal;

    public ReadMessagesTerminalCommand(final RetrieveMessagesDto retrieveMessagesDto, final MessagesRetriever messagesRetriever, final Terminal terminal) {
        this.retrieveMessagesDto = retrieveMessagesDto;
        this.messagesRetriever = messagesRetriever;
        this.terminal = terminal;
    }

    @Override
    public void execute() {
        try{
            terminal.write(messagesRetriever.execute(new User(retrieveMessagesDto.getUserName())));
        } catch (UserNotFound oops) {
            terminal.writeError(oops.getMessage());
        }
    }
}
