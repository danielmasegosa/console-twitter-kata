package com.danielmasegosa.e2e;

import com.danielmasegosa.Application;
import com.danielmasegosa.application.MessagesRetriever;
import com.danielmasegosa.application.PostCreator;
import com.danielmasegosa.application.UserSubscriber;
import com.danielmasegosa.application.UserWallRetriever;
import com.danielmasegosa.domain.repository.UserRepository;
import com.danielmasegosa.domain.time.Clock;
import com.danielmasegosa.infrastructure.persistence.InMemoryRepository;
import com.danielmasegosa.infrastructure.persistence.InMemoryUserRepository;
import com.danielmasegosa.infrastructure.terminal.Terminal;
import com.danielmasegosa.infrastructure.terminal.commands.CommandExecutor;
import com.danielmasegosa.infrastructure.terminal.commands.CommandGenerator;
import com.danielmasegosa.infrastructure.time.InternalClock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

public final class ViewUserWallE2E {

    private static final String QUIT = "\nquit";
    private static final String ENTER = "\n";
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final InMemoryRepository inMemoryRepository = new InMemoryRepository();
    private final UserRepository userRepository = new InMemoryUserRepository(inMemoryRepository);
    private final UserSubscriber userSubscriber = new UserSubscriber(userRepository);


    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setIn(System.in);
        System.setOut(System.out);
        inMemoryRepository.clear();
    }

    @Test
    void should_execute_a_post_command_and_save_a_new_message_to_the_database() {
        // given
        final var viewAliceWallCommand = "Alice wall";
        final var arrangeAndWallCommand = generateArrange() + viewAliceWallCommand + QUIT;

        // when
        initialization(arrangeAndWallCommand).startTerminal();

        // then
        assertTrue(outContent.toString().contains("Alice - I love the weather today"));
        assertTrue(outContent.toString().contains("Charlie - I'm in New York today! Anyone want to have a coffee?"));
    }

    public Application initialization(final String command) {
        final Clock clock = new InternalClock();
        final ByteArrayInputStream testIn = new ByteArrayInputStream(command.getBytes());
        final Scanner scanner = new Scanner(testIn);
        final Terminal terminal = new Terminal(clock, scanner);
        final MessagesRetriever messagesRetriever = new MessagesRetriever(userRepository);
        final PostCreator postCreator = new PostCreator(userRepository, clock);
        final UserWallRetriever userWallRetriever = new UserWallRetriever(userRepository);
        final CommandGenerator commandGenerator = new CommandGenerator(postCreator, messagesRetriever, userSubscriber, userWallRetriever, terminal);
        final CommandExecutor commandExecutor = new CommandExecutor(commandGenerator);

        return new Application(terminal, commandExecutor);
    }

    private String generateArrange() {
        final var alicePostMessageCommand = "Alice -> I love the weather today";
        final var charliePostMessageCommand = "Charlie -> I'm in New York today! Anyone want to have a coffee?";
        final var aliceFollowsCharlieCommand = "Alice follows Charlie";
        return alicePostMessageCommand + ENTER + charliePostMessageCommand + ENTER + aliceFollowsCharlieCommand + ENTER;
    }
}
