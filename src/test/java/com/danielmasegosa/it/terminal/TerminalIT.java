package com.danielmasegosa.it.terminal;

import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.domain.time.Clock;
import com.danielmasegosa.infrastructure.terminal.Terminal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.Instant;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public final class TerminalIT {

    private final Clock clock = mock(Clock.class);
    private  ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final InputStream systemIn = System.in;
    private final Scanner scanner = new Scanner(systemIn);

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setIn(systemIn);
        System.setOut(System.out);
        scanner.close();
    }

    @Test
    void should_allow_print_posts() {
        // given
        List<Post> posts = List.of(
                new Post(new User("aUserName"), "aMessage", Instant.parse("2021-05-22T00:05:00Z")),
                new Post(new User("aUserName"), "another Message", Instant.parse("2021-05-23T18:00:00Z"))
        );
        final var subject = new Terminal(clock, scanner);

        given(clock.now()).willReturn(Instant.parse("2021-05-22T18:05:00Z"));

        // when
        subject.write(posts);

        // then
        assertEquals("aUserName - aMessage (18 hours ago)\n" +
                                "aUserName - another Message (24 hours from now)\n", outContent.toString());
    }

    @Test
    void should_return_the_command_when_is_received_by_terminal() {
        // given
        final String command = "aCommand";

        final Terminal subject = provideTerminal(command);

        // when
        final String commandReceived = subject.readLine();

        // then
        assertEquals(command, commandReceived);
    }

    private Terminal provideTerminal(final String command) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(command.getBytes());
        final Scanner scanner = new Scanner(testIn);
        return new Terminal(clock, scanner);
    }
}
