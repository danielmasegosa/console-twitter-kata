package com.danielmasegosa.acceptance.steps;

import com.danielmasegosa.application.commands.PostCommand;
import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;

import java.util.List;

public final class World {

    private PostCommand postCommand;
    private User user;
    private List<Post> posts;
    private User subscriber;
    private User followee;

    public PostCommand getPostCommand() {
        return postCommand;
    }

    public void setPostCommand(PostCommand postCommand) {
        this.postCommand = postCommand;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setSubscriber(User subscriber) {
        this.subscriber = subscriber;
    }

    public User getSubscriber() {
        return subscriber;
    }

    public void setFollowee(User followee) {
        this.followee = followee;
    }

    public User getFollowee() {
        return followee;
    }

    public void reset() {
        this.setPostCommand(null);
        this.setUser(null);
        this.setPosts(null);
        this.setSubscriber(null);
        this.setFollowee(null);
    }
}
