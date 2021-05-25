package com.danielmasegosa.infrastructure.persistence.document;

import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;

import java.time.Instant;
import java.util.Objects;
import java.util.function.Function;

public final class PostDocument {

    private final String message;
    private Instant creationDate;

    public PostDocument(final String message, Instant creationDate) {
        this.message = message;
        this.creationDate = creationDate;
    }

    public String getMessage() {
        return message;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public static PostDocument fromDomain(final Post post) {
        return new PostDocument(post.getMessage(), post.getCreationDate());
    }

    public static Function<PostDocument, Post> toDomain(final String username) {
        return postDocument -> new Post(new User(username), postDocument.getMessage(), postDocument.getCreationDate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostDocument that = (PostDocument) o;
        return Objects.equals(message, that.message) && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, creationDate);
    }

    @Override
    public String toString() {
        return "PostDocument{" +
                "message='" + message + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
