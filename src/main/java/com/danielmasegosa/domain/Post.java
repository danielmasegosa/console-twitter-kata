package com.danielmasegosa.domain;

import java.time.Instant;
import java.util.Objects;

public final class Post {

    private final User user;
    private final String message;
    private Instant creationDate;

    public Post(final User user, final String message, final Instant creationDate) {
        this.user = user;
        this.message = message;
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(user, post.user) && Objects.equals(message, post.message) && Objects.equals(creationDate, post.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, message, creationDate);
    }

    @Override
    public String toString() {
        return "Post{" +
                "user=" + user +
                ", message='" + message + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
