package com.danielmasegosa.application;

import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.List;

import static com.danielmasegosa.fixtures.UserFixture.user;
import static org.mockito.BDDMockito.given;

public final class MessagesRetrieverTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final MessagesRetriever subject = new MessagesRetriever(userRepository);

    @Test
    void should_retrieve_all_the_messages_by_user() {
        // given
        final var aUser = user;
        final var userPosts = List.of(
                new Post(aUser, "aMessage", Instant.parse("2021-05-22T00:00:00Z")),
                new Post(aUser, "aMessage", Instant.parse("2021-05-22T00:05:00Z"))
        );
        given(userRepository.retrieveMessageByUser(aUser)).willReturn(userPosts);

        // when
        final List<Post> posts = subject.execute(aUser);

        // then
        Assertions.assertEquals(posts, userPosts);

    }
}
