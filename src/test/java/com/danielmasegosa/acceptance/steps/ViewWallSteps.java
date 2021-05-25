package com.danielmasegosa.acceptance.steps;

import com.danielmasegosa.application.UserWallRetriever;
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

public final class ViewWallSteps {

    private final World world = new World();
    private final InMemoryRepository inMemoryRepository = new InMemoryRepository();
    private final InMemoryUserRepository userRepository = new InMemoryUserRepository(inMemoryRepository);
    private final UserWallRetriever userWallRetriever = new UserWallRetriever(userRepository);

    @AfterEach
    void tearDown() {
        world.reset();
        inMemoryRepository.clear();
    }

    @Given("^a command to view the wall of a user$")
    public void a_user_write_a_command_to_view_the_wall(){
        final User alice = new User("Alice");
        userRepository.savePost(new Post(alice, "I love the weather today", Instant.parse("2021-05-22T00:05:00Z")));
        final User charlie = new User("Charlie");
        userRepository.savePost(new Post(charlie, "I'm in New York today! Anyone want to have a coffee?", Instant.parse("2021-05-22T00:10:00Z")));
        userRepository.saveSubscription(alice, charlie);

        final User user = new User("Alice");
        world.setUser(user);
    }

    @When("the view wall use case is fired with the received data")
    public void the_view_wall_use_case_is_executed() {
        final User user = world.getUser();
        List<Post> posts = userWallRetriever.execute(user);
        world.setPosts(posts);
    }

    @Then("the post from this user wall are retrieved")
    public void all_the_message_of_a_user_and_his_followee_are_retrieved() {
        final List<Post> posts = world.getPosts();
        assertThat(posts).hasSize(2);
        assertThat(posts.get(0).getUser().getUserName()).isEqualTo("Charlie");
        assertThat(posts.get(0).getMessage()).isEqualTo("I'm in New York today! Anyone want to have a coffee?");
        assertThat(posts.get(1).getUser().getUserName()).isEqualTo("Alice");
        assertThat(posts.get(1).getMessage()).isEqualTo("I love the weather today");
    }
}
