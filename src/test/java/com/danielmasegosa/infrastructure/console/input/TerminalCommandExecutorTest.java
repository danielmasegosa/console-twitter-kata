package com.danielmasegosa.infrastructure.console.input;

import com.danielmasegosa.application.MessagesRetriever;
import com.danielmasegosa.application.PostCreator;
import com.danielmasegosa.application.UserSubscriber;
import com.danielmasegosa.application.UserWallRetriever;
import com.danielmasegosa.application.commands.PostCommand;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.domain.repository.UserRepository;
import com.danielmasegosa.domain.time.Clock;
import com.danielmasegosa.infrastructure.console.input.dto.PostMessageDto;
import com.danielmasegosa.infrastructure.console.input.dto.RequestWallDto;
import com.danielmasegosa.infrastructure.console.input.dto.RetrieveMessagesDto;
import com.danielmasegosa.infrastructure.console.input.dto.SubscribtioDto;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public final class TerminalCommandExecutorTest {

    private final CommandGenerator commandGenerator = mock(CommandGenerator.class);
    private final CommandExecutor subject = new CommandExecutor(commandGenerator);

    @Test
    void should_execute_post_message_use_case() {
        // given
        final var postingCommand = "aCommand";

        final UserRepository userRepository = mock(UserRepository.class);
        final Clock clock = mock(Clock.class);
        final PostCreator postCreatorSpy = spy(new PostCreator(userRepository, clock));
        final TerminalCommand postMessageTerminalCommand = new PostMessageTerminalCommand(new PostMessageDto("aUserName", "aPostMessage"), postCreatorSpy);
        given(commandGenerator.execute(postingCommand)).willReturn(postMessageTerminalCommand);

        // when
        subject.execute(postingCommand);

        // then
        verify(postCreatorSpy).execute(new PostCommand("aUserName", "aPostMessage"));
    }

    @Test
    void should_execute_read_messages_use_case() {
        // given
        final var readMessagesCommand = "aCommand";

        final UserRepository userRepository = mock(UserRepository.class);
        final MessagesRetriever messagesRetriever = spy(new MessagesRetriever(userRepository));
        final TerminalCommand readMessagesTerminalCommand = new ReadMessagesTerminalCommand(new RetrieveMessagesDto("aUserName"), messagesRetriever);
        given(commandGenerator.execute(readMessagesCommand)).willReturn(readMessagesTerminalCommand);

        // when
        subject.execute(readMessagesCommand);

        // then
        verify(messagesRetriever).execute(new User("aUserName"));
    }

    @Test
    void should_execute_user_subscription_use_case() {
        // given
        final var subscribeCommand = "aCommand";

        final UserRepository userRepository = mock(UserRepository.class);
        final UserSubscriber userSubscriber = spy(new UserSubscriber(userRepository));
        final TerminalCommand subscriptionTerminalCommand = new SubscriptionTerminalCommand(new SubscribtioDto("aUserName", "subscribeTo"), userSubscriber);
        given(commandGenerator.execute(subscribeCommand)).willReturn(subscriptionTerminalCommand);

        // when
        subject.execute(subscribeCommand);

        // then
        verify(userSubscriber).execute(new User("aUserName"), new User("subscribeTo"));
    }

    @Test
    void should_execute_request_wall_use_case() {
        // given
        final var requestWallCommand = "aCommand";

        final UserRepository userRepository = mock(UserRepository.class);
        final UserWallRetriever userWallRetriever = spy(new UserWallRetriever(userRepository));
        final TerminalCommand viewWallTerminalCommand = new ViewWallTerminalCommand(new RequestWallDto("aUserName"), userWallRetriever);
        given(commandGenerator.execute(requestWallCommand)).willReturn(viewWallTerminalCommand);

        // when
        subject.execute(requestWallCommand);

        // then
        verify(userWallRetriever).execute(new User("aUserName"));
    }
}
