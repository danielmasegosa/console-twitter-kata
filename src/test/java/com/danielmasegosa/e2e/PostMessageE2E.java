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
import com.danielmasegosa.infrastructure.persistence.document.PostDocument;
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
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public final class PostMessageE2E {

    private static final String QUIT = "\nquit";
    private final InMemoryRepository inMemoryRepository = new InMemoryRepository();

    @BeforeEach
    void setUp() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
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
        final var postMessageTerminalCommand = "Alice -> I love the weather today"+ QUIT;

        // when
        initialization(postMessageTerminalCommand).startTerminal();

        // then
        final List<PostDocument> posts = inMemoryRepository.findPostsByUserName("Alice");
        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).getMessage()).isEqualTo("I love the weather today");
    }

    public Application initialization(final String command) {
        final Clock clock = new InternalClock();
        final ByteArrayInputStream testIn = new ByteArrayInputStream(command.getBytes());
        final Scanner scanner = new Scanner(testIn);
        final Terminal terminal = new Terminal(clock, scanner);
        final UserRepository userRepository = new InMemoryUserRepository(inMemoryRepository);
        final MessagesRetriever messagesRetriever = new MessagesRetriever(userRepository);
        final PostCreator postCreator = new PostCreator(userRepository, clock);
        final UserSubscriber userSubscriber = new UserSubscriber(userRepository);
        final UserWallRetriever userWallRetriever = new UserWallRetriever(userRepository);
        final CommandGenerator commandGenerator = new CommandGenerator(postCreator, messagesRetriever, userSubscriber, userWallRetriever, terminal);
        final CommandExecutor commandExecutor = new CommandExecutor(commandGenerator);

        return new Application(terminal, commandExecutor);
    }
}
