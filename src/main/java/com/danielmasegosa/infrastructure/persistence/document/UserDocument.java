package com.danielmasegosa.infrastructure.persistence.document;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class UserDocument {

    private final String userName;
    private final List<PostDocument> posts;
    private final Set<String> subscribedTo;

    public UserDocument(final String userName, final List<PostDocument> posts, final Set<String> subscribedTo) {
        this.userName = userName;
        this.posts = posts;
        this.subscribedTo = subscribedTo;
    }


    public UserDocument withSubscribers(Set<String> subscribedTo) {
        return new UserDocument(this.getUserName(), this.getPosts(), subscribedTo);
    }

    public String getUserName() {
        return userName;
    }

    public List<PostDocument> getPosts() {
        return posts;
    }

    public Set<String> getSubscribedTo() {
        return subscribedTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDocument that = (UserDocument) o;
        return Objects.equals(userName, that.userName) && Objects.equals(posts, that.posts) && Objects.equals(subscribedTo, that.subscribedTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, posts, subscribedTo);
    }

    @Override
    public String toString() {
        return "UserDocument{" +
                "userName='" + userName + '\'' +
                ", posts=" + posts +
                ", subscribedTo=" + subscribedTo +
                '}';
    }
}
