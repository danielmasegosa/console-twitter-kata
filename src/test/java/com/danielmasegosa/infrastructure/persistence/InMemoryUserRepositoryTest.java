package com.danielmasegosa.infrastructure.persistence;

import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.domain.repository.UserRepository;
import com.danielmasegosa.infrastructure.persistence.document.PostDocument;
import com.danielmasegosa.infrastructure.persistence.document.UserDocument;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.danielmasegosa.fixtures.UserFixture.followee;
import static com.danielmasegosa.fixtures.UserFixture.user;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public final class InMemoryUserRepositoryTest {

    private final InMemoryRepository inMemoryRepository = mock(InMemoryRepository.class);
    private final UserRepository subject = new InMemoryUserRepository(inMemoryRepository);

    @Test
    void should_call_to_in_memory_repository_with_the_proper_values_at_save_a_post() {
        // given
        final var aPost = new Post(user, "aMessage", Instant.parse("2021-05-22T00:05:00Z"));

        given(inMemoryRepository.findByUserName("aUserName")).willReturn(Optional.of(new UserDocument("aUserName", List.of(), emptySet())));

        // when
        subject.savePost(aPost);

        // then
        verify(inMemoryRepository)
                .savePost(aPost.getUser(), new PostDocument(
                        "aMessage",
                        Instant.parse("2021-05-22T00:05:00Z")
                ));
    }

    @Test
    void should_retrieve_all_the_messages_order_by_creation_date_for_a_user() {
        // given
        final var aUser = user;

        given(inMemoryRepository.findPostsByUserName(user.getUserName()))
                .willReturn(
                        List.of(
                                new PostDocument("aMessage", Instant.parse("2021-05-22T00:05:00Z")),
                                new PostDocument("aMessage", Instant.parse("2021-05-22T00:10:00Z")))
                );

        // when
        List<Post> posts = subject.retrieveMessageByUser(aUser);

        // then
        assertThat(posts).contains(
                new Post(user, "aMessage", Instant.parse("2021-05-22T00:05:00Z")),
                new Post(user, "aMessage", Instant.parse("2021-05-22T00:10:00Z"))
        );
    }

    @Test
    void should_subscribe_to_a_user() {
        // given
        final var aUser = user;
        final var aFollowee = followee;

        // when
        subject.saveSubscription(aUser, aFollowee);

        // then
        verify(inMemoryRepository).saveSubscription(user.getUserName(), followee.getUserName());
    }

    @Test
    void should_retrieve_all_the_message_of_a_user_followees() {
        // given
        final User aUser = user;
        final User aFollowee = followee;
        final User anotherFollowee = new User("anotherFollowee");

        given(inMemoryRepository.findByUserName(user.getUserName())).willReturn(
                Optional.of(new UserDocument(
                                user.getUserName(),
                                List.of(new PostDocument("aMessage", Instant.parse("2021-05-22T00:06:00Z")),
                                        new PostDocument("aMessage", Instant.parse("2021-05-21T23:55:00Z"))
                                ),
                                Set.of(aFollowee.getUserName(),
                                        anotherFollowee.getUserName())
                        )
                )
        );

        given(inMemoryRepository.findPostsByUserName("aFollowee")).willReturn(List.of(new PostDocument("aMessage", Instant.parse("2021-05-22T00:10:00Z"))));
        given(inMemoryRepository.findPostsByUserName("anotherFollowee")).willReturn(List.of(new PostDocument("aMessage", Instant.parse("2021-05-22T00:05:00Z"))));

        // when
        final List<Post> posts = subject.retrieveUserWall(aUser);

        // then
        assertThat(posts).contains(
                new Post(user, "aMessage", Instant.parse("2021-05-21T23:55:00Z")),
                new Post(anotherFollowee, "aMessage", Instant.parse("2021-05-22T00:05:00Z")),
                new Post(user, "aMessage", Instant.parse("2021-05-22T00:06:00Z")),
                new Post(aFollowee, "aMessage", Instant.parse("2021-05-22T00:10:00Z"))
        );
    }

    @Test
    void should_retrieve_the_messages_of_an_user_for_the_wall_when_has_not_followees() {
        // given
        final User aUser = user;

        given(inMemoryRepository.findByUserName(user.getUserName())).willReturn(
                Optional.of(new UserDocument(
                                user.getUserName(),
                                List.of(new PostDocument("aMessage", Instant.parse("2021-05-22T00:06:00Z")),
                                        new PostDocument("aMessage", Instant.parse("2021-05-21T23:55:00Z"))
                                ),
                                Set.of()
                        )
                )
        );

        // when
        final List<Post> posts = subject.retrieveUserWall(aUser);

        // then
        assertThat(posts).contains(
                new Post(user, "aMessage", Instant.parse("2021-05-21T23:55:00Z")),
                new Post(user, "aMessage", Instant.parse("2021-05-22T00:06:00Z"))
        );
    }

    @Test
    void should__not_retrieve_messages_when_the_user_has_not_created_messages_yet() {
        // given
        final User aUser = user;

        given(inMemoryRepository.findByUserName(user.getUserName())).willReturn(Optional.empty());

        // when
        final List<Post> posts = subject.retrieveUserWall(aUser);

        // then
        assertThat(posts).isEmpty();
    }
}
