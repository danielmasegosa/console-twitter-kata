package com.danielmasegosa.it.terminal;

import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.domain.time.Clock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Instant;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public final class TerminalIT {

    private final Clock clock = mock(Clock.class);
    private final Scanner scanner = new Scanner(System.in);
    private final Terminal terminal = new Terminal(clock, scanner);
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
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

        given(clock.now()).willReturn(Instant.parse("2021-05-22T18:05:00Z"));

        // when
        terminal.write(posts);

        // then
        assertEquals("aUserName - aMessage (18 hours ago)\n" +
                                "aUserName - another Message (24 hours from now)\n", outContent.toString());
    }
}
