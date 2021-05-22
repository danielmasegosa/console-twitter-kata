package com.danielmasegosa.application;

import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.domain.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Set;

import static org.mockito.BDDMockito.given;

public final class UserWallViewerTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserWallViewer subject = new UserWallViewer(userRepository);

    @Test
    void should_retrieve_all_the_messages_from_the_users_that_an_user_is_subscribed_to() {
        // given
        final var aUser = new User("aUserName");
        final var bob = new User("Bob");
        final var alice = new User("Alice");
        final var aUserWall = Set.of(
                new Post(bob, "A Bob message", Instant.parse("2021-05-22T00:05:00Z")),
                new Post(bob, "A Bob message", Instant.parse("2021-05-22T12:00:00Z")),
                new Post(alice, "An Alice message", Instant.parse("2021-05-22T00:10:00Z"))
        );

        given(userRepository.retrieveUserWall(aUser)).willReturn(aUserWall);

        // when
        var posts = subject.execute(aUser);

        // then
        Assertions.assertEquals(aUserWall, posts);
    }
}
