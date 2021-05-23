package com.danielmasegosa.application;

import com.danielmasegosa.application.commands.PostCommand;
import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.repository.UserRepository;
import com.danielmasegosa.domain.time.Clock;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static com.danielmasegosa.fixtures.UserFixture.user;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public final class PostCreatorTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final Clock clock = mock(Clock.class);
    private final PostCreator subject = new PostCreator(userRepository, clock);

    @Test
    void should_save_a_posted_message() {
        // given
        final var aPostCommand = new PostCommand(user.getUserName(), "aPostMessage");

        given(clock.now()).willReturn(Instant.parse("2021-05-22T00:05:00Z"));

        // when
        subject.execute(aPostCommand);

        // then
        verify(userRepository).savePost(new Post(user, "aPostMessage", Instant.parse("2021-05-22T00:05:00Z")));
    }
}
