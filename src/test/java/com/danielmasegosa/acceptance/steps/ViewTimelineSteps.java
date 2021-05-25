package com.danielmasegosa.acceptance.steps;

import com.danielmasegosa.application.MessagesRetriever;
import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.infrastructure.persistence.InMemoryRepository;
import com.danielmasegosa.infrastructure.persistence.InMemoryUserRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.AfterEach;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public final class ViewTimelineSteps {

    private final World world = new World();
    private final InMemoryRepository inMemoryRepository = new InMemoryRepository();
    private final InMemoryUserRepository userRepository = new InMemoryUserRepository(inMemoryRepository);
    private final MessagesRetriever messagesRetriever = new MessagesRetriever(userRepository);

    @AfterEach
    void tearDown() {
        world.reset();
        inMemoryRepository.clear();
    }

    @Given("^a command to view the timeline of a user$")
    public void a_user_write_a_command_to_view_the_timeline(){
        userRepository.savePost(new Post(new User("Alice"), "I love the weather today", Instant.parse("2021-05-22T00:05:00Z")));

        final User user = new User("Alice");
        world.setUser(user);
    }

    @When("the view timeline use case is fired with the received data")
    public void the_view_timeline_use_case_is_executed() {
        final User user = world.getUser();
        List<Post> posts = messagesRetriever.execute(user);
        world.setPosts(posts);
    }

    @Then("the post from this user are retrieved")
    public void the_post_to_list_for_the_user_contains_the_post_messages() {
        final List<Post> posts = world.getPosts();
        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).getUser().getUserName()).isEqualTo("Alice");
        assertThat(posts.get(0).getMessage()).isEqualTo("I love the weather today");
    }
}
