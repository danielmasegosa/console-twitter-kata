package com.danielmasegosa.infrastructure.persistence.document;

import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;

import java.time.Instant;
import java.util.Objects;

public final class PostDocument {

    private final String userName;
    private final String message;
    private Instant creationDate;

    public PostDocument(final String userName, final String message, Instant creationDate) {
        this.userName = userName;
        this.message = message;
        this.creationDate = creationDate;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public static PostDocument fromDomain(final Post post) {
        return new PostDocument(post.getUser().getUserName(), post.getMessage(), post.getCreationDate());
    }

    public static Post toDomain(final PostDocument postDocument) {
        return new Post(new User(postDocument.userName), postDocument.message, postDocument.creationDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostDocument that = (PostDocument) o;
        return Objects.equals(userName, that.userName) && Objects.equals(message, that.message) && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, message, creationDate);
    }

    @Override
    public String toString() {
        return "PostDocument{" +
                "userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
