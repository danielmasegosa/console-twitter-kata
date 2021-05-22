package com.danielmasegosa.domain;

import java.time.Instant;
import java.util.Objects;

public final class Post {

    private final User user;
    private final String message;
    private Instant creationTime;

    public Post(final User user, final String message) {
        this.user = user;
        this.message = message;
    }

    public Post(final User user, final String message, final Instant creationTime) {
        this.user = user;
        this.message = message;
        this.creationTime = creationTime;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(user, post.user) && Objects.equals(message, post.message) && Objects.equals(creationTime, post.creationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, message, creationTime);
    }

    @Override
    public String toString() {
        return "Post{" +
                "user=" + user +
                ", message='" + message + '\'' +
                ", creationTime=" + creationTime +
                '}';
    }
}
