package com.danielmasegosa.it.console;

import com.danielmasegosa.application.MessagesRetriever;
import com.danielmasegosa.application.PostCreator;
import com.danielmasegosa.application.UserSubscriber;
import com.danielmasegosa.application.UserWallRetriever;
import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.domain.repository.UserRepository;
import com.danielmasegosa.domain.time.Clock;
import com.danielmasegosa.infrastructure.console.input.CommandExecutor;
import com.danielmasegosa.infrastructure.console.input.CommandGenerator;
import com.danielmasegosa.infrastructure.persistence.InMemoryRepository;
import com.danielmasegosa.infrastructure.persistence.InMemoryUserRepository;
import com.danielmasegosa.infrastructure.persistence.document.PostDocument;
import com.danielmasegosa.infrastructure.persistence.document.UserDocument;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public final class TerminalCommandExecutorIT {

    private final InMemoryRepository inMemoryRepository = new InMemoryRepository();
    private final UserRepository userRepository = new InMemoryUserRepository(inMemoryRepository);
    private final Clock clock = mock(Clock.class);
    private final MessagesRetriever messagesRetriever = new MessagesRetriever(userRepository);
    private final MessagesRetriever messagesRetrieverSpy = spy(this.messagesRetriever);
    private final PostCreator postCreator = new PostCreator(userRepository, clock);
    private final UserSubscriber userSubscriber = new UserSubscriber(userRepository);
    private final UserWallRetriever userWallRetriever = new UserWallRetriever(userRepository);
    private final UserWallRetriever userWallRetrieverSpy = spy(userWallRetriever);
    private final CommandGenerator commandGenerator = new CommandGenerator(postCreator, messagesRetrieverSpy, userSubscriber, userWallRetrieverSpy);
    private final CommandExecutor subject = new CommandExecutor(commandGenerator);

    @AfterEach
    void tearDown() {
        inMemoryRepository.clear();
    }

    @Test
    void should_save_a_post() {
        // given
        final String registerPostCommand = "Alice -> I love the weather today";

        given(clock.now()).willReturn(Instant.parse("2021-05-22T00:05:00Z"));

        // when
        subject.execute(registerPostCommand);

        // then
        final List<PostDocument> alicePosts = inMemoryRepository.findPostsByUserName("Alice");
        assertThat(alicePosts).contains(new PostDocument("Alice", "I love the weather today", Instant.parse("2021-05-22T00:05:00Z")));
    }

    @Test
    void should_return_the_post_of_a_user() {
        // given
        final String viewTimeLine = "Alice";

        given(clock.now()).willReturn(Instant.parse("2021-05-22T00:05:00Z"));
        final String registerPostCommand = "Alice -> I love the weather today";
        subject.execute(registerPostCommand);

        // when
        subject.execute(viewTimeLine);

        // then
        final List<Post> posts = messagesRetrieverSpy.execute(new User("Alice"));
        assertThat(posts).contains(new Post(new User("Alice"), "I love the weather today", Instant.parse("2021-05-22T00:05:00Z")));
    }

    @Test
    void should_subscribe_to_a_user() {
        // given
        final String followsCommand = "Charlie follows Alice";

        given(clock.now()).willReturn(Instant.parse("2021-05-22T00:05:00Z"));
        final String registerAlicePostCommand = "Alice -> I love the weather today";
        subject.execute(registerAlicePostCommand);
        final String registerPostCommand = "Charlie -> I'm in New York today! Anyone want to have a coffee?";
        subject.execute(registerPostCommand);

        // when
        subject.execute(followsCommand);

        // then
        final Optional<UserDocument> maybeUser = inMemoryRepository.findByUserName("Charlie");
        assertTrue(maybeUser.isPresent());
        final UserDocument userDocument = maybeUser.get();
        assertThat(userDocument.getSubscribedTo()).contains("Alice");
    }

    @Test
    void should_retrieve_the_wall_of_a_user() {
        // given
        final String wallCommand = "Charlie wall";

        given(clock.now()).willReturn(Instant.parse("2021-05-22T00:05:00Z"));
        final String followeePost = "Alice -> I love the weather today";
        subject.execute(followeePost);
        given(clock.now()).willReturn(Instant.parse("2021-05-22T00:15:00Z"));
        final String anotherFolloweePost = "Alice -> I love the weather today again";
        subject.execute(anotherFolloweePost);
        given(clock.now()).willReturn(Instant.parse("2021-05-22T00:20:00Z"));
        final String aPost = "Charlie -> I'm in New York today! Anyone want to have a coffee?";
        subject.execute(aPost);
        final String followsCommand = "Charlie follows Alice";
        subject.execute(followsCommand);

        // when
        subject.execute(wallCommand);

        // then
        final List<Post> charlieWall = userWallRetrieverSpy.execute(new User("Charlie"));
        assertThat(charlieWall).containsExactly(
                new Post(new User("Alice"), "I love the weather today", Instant.parse("2021-05-22T00:05:00Z")),
                new Post(new User("Alice"), "I love the weather today again", Instant.parse("2021-05-22T00:15:00Z")),
                new Post(new User("Charlie"), "I'm in New York today! Anyone want to have a coffee?", Instant.parse("2021-05-22T00:20:00Z"))
        );
    }
}
