package com.danielmasegosa.application;

import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.domain.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public final class PostCreatorTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final PostMessageUseCase subject = new PostMessageUseCase(userRepository);

    @DisplayName("Should save a post when the use case is executed")
    @Test
    void should_save_a_posted_message() {
        // given
        final var aPost = new Post(new User("aUserName"), "aPostMessage");

        // when
        subject.execute(aPost);

        // then
        verify(userRepository).save(aPost);
    }
}
