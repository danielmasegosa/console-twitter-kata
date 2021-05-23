package com.danielmasegosa.infrastructure.console.input;

import com.danielmasegosa.application.MessagesRetriever;
import com.danielmasegosa.application.PostCreator;
import com.danielmasegosa.application.UserSubscriber;
import com.danielmasegosa.application.UserWallRetriever;
import com.danielmasegosa.infrastructure.console.input.dto.PostMessageDto;
import com.danielmasegosa.infrastructure.console.input.dto.RequestWallDto;
import com.danielmasegosa.infrastructure.console.input.dto.RetrieveMessagesDto;
import com.danielmasegosa.infrastructure.console.input.dto.SubscribtioDto;

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

    public CommandGenerator(final PostCreator postCreator, final MessagesRetriever messagesRetriever,
                            final UserSubscriber userSubscriber, final UserWallRetriever userWallRetriever) {
        this.postCreator = postCreator;
        this.messagesRetriever = messagesRetriever;
        this.userSubscriber = userSubscriber;
        this.userWallRetriever = userWallRetriever;
    }

    public TerminalCommand execute(final String command) {
        if (matches(POST_MESSAGE_COMMAND_PATTERN, command)) {
            final String[] commandData = command.split(POST_MESSAGE_DATA_DELIMITER);
            final var userName = commandData[0];
            final var postMessage = commandData[1];
            return new PostMessageTerminalCommand(new PostMessageDto(userName, postMessage), postCreator);
        } else if(matches(SUBSCRIBE_COMMAND_PATTERN, command)){
            final String[] commandData = command.split(SUBSCRIBE_DATA_DELIMITER);
            final var userName = commandData[0];
            final var subscribeTo = commandData[1];
            return new SubscriptionTerminalCommand(new SubscribtioDto(userName.trim(), subscribeTo.trim()), userSubscriber);
        } else if(matches(VIEW_WALL_COMMAND_PATTERN, command)){
            final String[] commandData = command.split(RETRIEVE_WALL_DATA_DELIMITER);
            final var userName = commandData[0];
            return new ViewWallTerminalCommand(new RequestWallDto(userName.trim()), userWallRetriever);
        } else {
            final var userName = command.trim();
            return new ReadMessagesTerminalCommand(new RetrieveMessagesDto(userName), messagesRetriever);
        }
    }
}
