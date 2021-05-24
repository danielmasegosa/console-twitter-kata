package com.danielmasegosa.acceptance.steps;

import com.danielmasegosa.application.PostCreator;
import com.danielmasegosa.application.commands.PostCommand;
import com.danielmasegosa.infrastructure.persistence.InMemoryRepository;
import com.danielmasegosa.infrastructure.persistence.InMemoryUserRepository;
import com.danielmasegosa.infrastructure.persistence.document.PostDocument;
import com.danielmasegosa.infrastructure.time.InternalClock;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.AfterEach;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public final class PostMessageSteps {

    private final World world = new World();
    private final InternalClock clock = new InternalClock();
    private final InMemoryRepository inMemoryRepository = new InMemoryRepository();
    private final InMemoryUserRepository userRepository = new InMemoryUserRepository(inMemoryRepository);
    private final PostCreator postCreator = new PostCreator(userRepository, clock);

    @AfterEach
    void tearDown() {
        world.reset();
        inMemoryRepository.clear();
    }

    @Given("^a command to post a message$")
    public void a_user_write_a_command_to_post_a_message(){
        final PostCommand postCommand = new PostCommand("Alice", "I love the weather today");
        world.setPostCommand(postCommand);
    }

    @When("the use case is fired with the received data")
    public void the_create_post_use_case_is_executed() {
        final PostCommand postCommand = world.getPostCommand();
        postCreator.execute(postCommand);
    }

    @Then("the post is stored in the database")
    public void the_posts_for_the_user_are_stored() {
        final List<PostDocument> posts = inMemoryRepository.findPostsByUserName("Alice");
        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).getUserName()).isEqualTo("Alice");
        assertThat(posts.get(0).getMessage()).isEqualTo("I love the weather today");
    }
}
