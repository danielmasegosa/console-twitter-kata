package com.danielmasegosa.infrastructure.console.input;

import com.danielmasegosa.application.MessagesRetriever;
import com.danielmasegosa.application.PostCreator;
import com.danielmasegosa.application.UserSubscriber;
import com.danielmasegosa.application.UserWallRetriever;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public final class TerminalCommandGeneratorTest {

    public PostCreator postCreator = mock(PostCreator.class);
    public MessagesRetriever messagesRetriever = mock(MessagesRetriever.class);
    public UserSubscriber userSubscriber = mock(UserSubscriber.class);
    public UserWallRetriever userWallRetriever = mock(UserWallRetriever.class);
    public final CommandGenerator subject = new CommandGenerator(postCreator, messagesRetriever, userSubscriber, userWallRetriever);

    @Test
    void should_generate_a_post_message_command() {
        // given
        final var aPostCommand = "Alice -> I love the weather today";

        // when
        final TerminalCommand generatedTerminalCommand = subject.execute(aPostCommand);

        // then
        assertTrue(generatedTerminalCommand instanceof PostMessageTerminalCommand);
    }

    @Test
    void should_generate_a_read_messages_command() {
        // given
        final var aReadMessagesCommand = "Alice";

        // when
        final TerminalCommand generatedTerminalCommand = subject.execute(aReadMessagesCommand);

        // then
        assertTrue(generatedTerminalCommand instanceof ReadMessagesTerminalCommand);
    }

    @Test
    void should_generate_a_subscribe_to_user_command() {
        // given
        final var aSubscriptionCommand = "Charlie follows Alice";

        // when
        final TerminalCommand generatedTerminalCommand = subject.execute(aSubscriptionCommand);

        // then
        assertTrue(generatedTerminalCommand instanceof SubscriptionTerminalCommand);
    }

    @Test
    void should_generate_a_wall_command() {
        // given
        final var aViewWallCommand = "Charlie wall";

        // when
        final TerminalCommand generatedTerminalCommand = subject.execute(aViewWallCommand);

        // then
        assertTrue(generatedTerminalCommand instanceof ViewWallTerminalCommand);
    }
}
