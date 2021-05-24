package com.danielmasegosa.infrastructure.terminal.commands;

import com.danielmasegosa.application.MessagesRetriever;
import com.danielmasegosa.application.PostCreator;
import com.danielmasegosa.application.UserSubscriber;
import com.danielmasegosa.application.UserWallRetriever;
import com.danielmasegosa.infrastructure.terminal.Terminal;
import com.danielmasegosa.infrastructure.terminal.commands.dto.PostMessageDto;
import com.danielmasegosa.infrastructure.terminal.commands.dto.RequestWallDto;
import com.danielmasegosa.infrastructure.terminal.commands.dto.RetrieveMessagesDto;
import com.danielmasegosa.infrastructure.terminal.commands.dto.SubscribtioDto;

import static java.util.regex.Pattern.matches;

public class CommandGenerator {

    private static final String POST_MESSAGE_COMMAND_PATTERN = "(.*)\\s->\\s(.*)";
    public static final String POST_MESSAGE_DATA_DELIMITER = "->";
    private static final String SUBSCRIBE_COMMAND_PATTERN = "(.*)\\sfollows\\s(.*)";
    public static final String SUBSCRIBE_DATA_DELIMITER = "follows";
    private static final String VIEW_WALL_COMMAND_PATTERN = "(.*)\\swall";
    public static final String RETRIEVE_WALL_DATA_DELIMITER = "wall";

    private final PostCreator postCreator;
    private MessagesRetriever messagesRetriever;
    private UserSubscriber userSubscriber;
    private UserWallRetriever userWallRetriever;
    private Terminal terminal;

    public CommandGenerator(final PostCreator postCreator, final MessagesRetriever messagesRetriever,
                            final UserSubscriber userSubscriber, final UserWallRetriever userWallRetriever, final Terminal terminal) {
        this.postCreator = postCreator;
        this.messagesRetriever = messagesRetriever;
        this.userSubscriber = userSubscriber;
        this.userWallRetriever = userWallRetriever;
        this.terminal = terminal;
    }

    public TerminalCommand execute(final String command) {
        if (matches(POST_MESSAGE_COMMAND_PATTERN, command)) {
            final String[] commandData = command.split(POST_MESSAGE_DATA_DELIMITER);
            final var userName = commandData[0];
            final var postMessage = commandData[1];
            final PostMessageDto dto = new PostMessageDto(userName, postMessage);
            return new PostMessageTerminalCommand(dto, postCreator, terminal);
        } else if(matches(SUBSCRIBE_COMMAND_PATTERN, command)){
            final String[] commandData = command.split(SUBSCRIBE_DATA_DELIMITER);
            final var userName = commandData[0];
            final var subscribeTo = commandData[1];
            final SubscribtioDto subscriptionDto = new SubscribtioDto(userName, subscribeTo);
            return new SubscriptionTerminalCommand(subscriptionDto, userSubscriber, terminal);
        } else if(matches(VIEW_WALL_COMMAND_PATTERN, command)){
            final String[] commandData = command.split(RETRIEVE_WALL_DATA_DELIMITER);
            final var userName = commandData[0];
            return new ViewWallTerminalCommand(new RequestWallDto(userName.trim()), userWallRetriever, terminal);
        } else {
            final var userName = command.trim();
            return new ReadMessagesTerminalCommand(new RetrieveMessagesDto(userName), messagesRetriever, terminal);
        }
    }
}
