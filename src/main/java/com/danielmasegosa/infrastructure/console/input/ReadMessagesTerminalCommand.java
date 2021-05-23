package com.danielmasegosa.infrastructure.console.input;

import com.danielmasegosa.application.MessagesRetriever;
import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.infrastructure.console.input.dto.RetrieveMessagesDto;

import java.util.List;

public class ReadMessagesTerminalCommand implements TerminalCommand {
    private final RetrieveMessagesDto retrieveMessagesDto;
    private final MessagesRetriever messagesRetriever;

    public ReadMessagesTerminalCommand(final RetrieveMessagesDto retrieveMessagesDto, final MessagesRetriever messagesRetriever) {
        this.retrieveMessagesDto = retrieveMessagesDto;
        this.messagesRetriever = messagesRetriever;
    }

    @Override
    public void execute() {
        final List<Post> execute = messagesRetriever.execute(new User(retrieveMessagesDto.getUserName()));
    }
}
