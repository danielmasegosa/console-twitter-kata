package com.danielmasegosa.application;

import com.danielmasegosa.domain.User;
import com.danielmasegosa.domain.UserRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public final class UserSubscriberTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserSubscriber subject = new UserSubscriber(userRepository);

    @Test
    void should_subscribe_to_a_user() {
        // given
        final var aFollower = new User("aSubscriberUserName");
        final var aFollowee = new User("aFolloweeUserName");

        // when
        subject.execute(aFollower, aFollowee);

        // then
        verify(userRepository).subscribeToUser(aFollower, aFollowee);
    }
}
