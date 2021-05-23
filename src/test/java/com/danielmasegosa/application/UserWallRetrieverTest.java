package com.danielmasegosa.application;

import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.List;

import static com.danielmasegosa.fixtures.UserFixture.createUser;
import static com.danielmasegosa.fixtures.UserFixture.user;
import static org.mockito.BDDMockito.given;

public final class UserWallRetrieverTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserWallRetriever subject = new UserWallRetriever(userRepository);

    @Test
    void should_retrieve_all_the_messages_from_the_users_that_an_user_is_subscribed_to() {
        // given
        final var aUser = user;
        final var bob = createUser("Bob");
        final var alice = createUser("Alice");
        final var aUserWall = List.of(
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
