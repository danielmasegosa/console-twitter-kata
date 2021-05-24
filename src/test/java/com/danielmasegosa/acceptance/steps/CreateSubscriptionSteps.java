package com.danielmasegosa.acceptance.steps;

import com.danielmasegosa.application.UserSubscriber;
import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;
import com.danielmasegosa.infrastructure.persistence.InMemoryRepository;
import com.danielmasegosa.infrastructure.persistence.InMemoryUserRepository;
import com.danielmasegosa.infrastructure.persistence.document.UserDocument;
import com.danielmasegosa.infrastructure.time.InternalClock;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.AfterEach;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class CreateSubscriptionSteps {

    private final World world = new World();
    private final InternalClock clock = new InternalClock();
    private final InMemoryRepository inMemoryRepository = new InMemoryRepository();
    private final InMemoryUserRepository userRepository = new InMemoryUserRepository(inMemoryRepository);
    private final UserSubscriber userSubscriber = new UserSubscriber(userRepository);

    @AfterEach
    void tearDown() {
        world.reset();
        inMemoryRepository.clear();
    }

    @Given("^a command to to subscribe to a user$")
    public void a_user_write_a_command_to_subscribe_to_another(){
        userRepository.savePost(new Post(new User("Alice"), "I love the weather today", Instant.parse("2021-05-22T00:05:00Z")));
        userRepository.savePost(new Post(new User("Charlie"), "I'm in New York today! Anyone want to have a coffee?", Instant.parse("2021-05-22T00:05:00Z")));

        final User subscriber = new User("Alice");
        final User followee = new User("Charlie");
        world.setSubscriber(subscriber);
        world.setFollowee(followee);
    }

    @When("create subscription use case is fired with the received data")
    public void the_create_subscription_use_case_is_executed() {
        final User subscriber = world.getSubscriber();
        final User followee = world.getFollowee();
        userSubscriber.execute(subscriber, followee);
    }

    @Then("the list of the followee from this user are retrieved")
    public void the_subscriber_to_list_for_the_user_contains_the_number_of_the_new_followee() {
        final Optional<UserDocument> maybeUser = inMemoryRepository.findByUserName(world.getSubscriber().getUserName());
        assertTrue(maybeUser.isPresent());
        final UserDocument userDocument = maybeUser.get();
        assertThat(userDocument.getSubscribedTo()).contains("Charlie");
    }
}
