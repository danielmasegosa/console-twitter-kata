package com.danielmasegosa.application.commands;

import java.util.Objects;

public final class PostCommand {

    private final String userName;

    private final String message;

    public PostCommand(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostCommand that = (PostCommand) o;
        return Objects.equals(userName, that.userName) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, message);
    }

    @Override
    public String toString() {
        return "PostCommand{" +
                "userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
