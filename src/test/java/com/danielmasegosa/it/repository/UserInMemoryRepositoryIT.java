package com.danielmasegosa.it.console;

import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.domain.repository.UserRepository;
import com.danielmasegosa.infrastructure.persistence.InMemoryRepository;
import com.danielmasegosa.infrastructure.persistence.InMemoryUserRepository;
import com.danielmasegosa.infrastructure.persistence.document.PostDocument;
import com.danielmasegosa.infrastructure.persistence.document.UserDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.danielmasegosa.fixtures.UserFixture.followee;
import static com.danielmasegosa.fixtures.UserFixture.user;
import static org.assertj.core.api.Assertions.assertThat;


public final class UserInMemoryRepositoryIT {

    private final InMemoryRepository inMemoryRepository = new InMemoryRepository();
    private final UserRepository subject = new InMemoryUserRepository(inMemoryRepository);

    @BeforeEach
    void setUp() {
        inMemoryRepository.clear();
    }

    @Test
    void should_save_a_post() {
        // given
        final var aPost = new Post(user, "aMessage", Instant.parse("2021-05-22T00:05:00Z"));

        // when
        subject.savePost(aPost);

        // then
        List<PostDocument> userPosts = inMemoryRepository.findPostsByUserName(user.getUserName());
        assertThat(userPosts).hasSize(1);
    }

    @Test
    void should_save_a_post_with_the_proper_values() {
        // given
        final var aPost = new Post(user, "aMessage", Instant.parse("2021-05-22T00:05:00Z"));

        // when
        subject.savePost(aPost);

        // then
        List<PostDocument> userPosts = inMemoryRepository.findPostsByUserName(user.getUserName());
        assertThat(userPosts).contains(new PostDocument("aUserName", "aMessage", Instant.parse("2021-05-22T00:05:00Z")));
    }

    @Test
    void should_save_a_post_with_the_proper_values_when_exists_another_posts_for_a_user() {
        // given
        final var aPost = new Post(user, "aMessage", Instant.parse("2021-05-22T00:05:00Z"));

        subject.savePost(new Post(user, "aMessage", Instant.parse("2021-05-22T00:10:00Z")));

        // when
        subject.savePost(aPost);

        // then
        List<PostDocument> userPosts = inMemoryRepository.findPostsByUserName(user.getUserName());
        assertThat(userPosts).contains(
                new PostDocument("aUserName", "aMessage", Instant.parse("2021-05-22T00:05:00Z")),
                new PostDocument("aUserName", "aMessage", Instant.parse("2021-05-22T00:10:00Z"))
        );
    }

    @Test
    void should_return_the_posts_for_a_user() {
        // given
        final var aUser = new User("aUserName");

        subject.savePost(new Post(user, "aMessage", Instant.parse("2021-05-22T00:10:00Z")));

        // when
        List<Post> posts = subject.retrieveMessageByUser(aUser);

        // then
        assertThat(posts).contains(
                new Post(new User("aUserName"), "aMessage", Instant.parse("2021-05-22T00:10:00Z"))
        );
    }

    @Test
    void should_return_an_empty_posts_list_when_doesnt_exist_post_for_the_required_user() {
        // given
        final var aUser = new User("aUserName");

        // when
        List<Post> posts = subject.retrieveMessageByUser(aUser);

        // then
        assertThat(posts).isEmpty();
    }

    @Test
    void should_subscribe_to_an_existent_user() {
        // given
        final User aSubscriberUser = new User("aSubscriberUser");
        final User aFolloweeUser = new User("aFolloweeUser");

        subject.savePost(new Post(aSubscriberUser, "aMessage", Instant.parse("2021-05-22T00:10:00Z")));
        subject.savePost(new Post(aFolloweeUser, "aMessage", Instant.parse("2021-05-22T00:10:00Z")));

        // when
        subject.saveSubscription(aSubscriberUser, aFolloweeUser);

        // then
        final Optional<UserDocument> maybeUserName = inMemoryRepository.findByUserName(aSubscriberUser.getUserName());
        Assertions.assertTrue(maybeUserName.isPresent());
        final UserDocument user = maybeUserName.get();
        assertThat(user.getSubscribedTo()).anyMatch(username -> username.equals("aFolloweeUser"));
    }

    @Test
    void should_not_subscribe_if_the_user_doesnt_exist() {
        // given
        final User aSubscriberUser = new User("aSubscriberUser");
        final User aFolloweeUser = new User("aFolloweeUser");

        subject.savePost(new Post(aSubscriberUser, "aMessage", Instant.parse("2021-05-22T00:10:00Z")));

        // when
        subject.saveSubscription(aSubscriberUser, aFolloweeUser);

        // then
        final Optional<UserDocument> maybeUserName = inMemoryRepository.findByUserName(aSubscriberUser.getUserName());
        Assertions.assertTrue(maybeUserName.isPresent());
        final UserDocument user = maybeUserName.get();
        assertThat(user.getSubscribedTo()).isEmpty();
    }

    @Test
    void should_retrieve_all_the_messages_of_the_user_and_the_followees() {
        // given
        final var aUser = user;
        final var aFollowee = followee;
        final var anotherFollowee = new User("anotherFollowee");

        subject.savePost(new Post(aUser, "aMessage", Instant.parse("2021-05-22T00:10:00Z")));
        subject.savePost(new Post(aFollowee, "aMessage", Instant.parse("2021-05-22T00:10:00Z")));
        subject.savePost(new Post(anotherFollowee, "aMessage", Instant.parse("2021-05-22T00:05:00Z")));

        subject.saveSubscription(aUser, aFollowee);
        subject.saveSubscription(aUser, anotherFollowee);

        // when
        final List<Post> wall = subject.retrieveUserWall(aUser);

        // then
        assertThat(wall).contains(
                new Post(anotherFollowee, "aMessage", Instant.parse("2021-05-22T00:05:00Z")),
                new Post(aUser, "aMessage", Instant.parse("2021-05-22T00:10:00Z")),
                new Post(aFollowee, "aMessage", Instant.parse("2021-05-22T00:10:00Z"))
        );
    }
}
