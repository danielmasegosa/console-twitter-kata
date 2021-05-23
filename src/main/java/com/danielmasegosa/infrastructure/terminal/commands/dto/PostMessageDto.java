package com.danielmasegosa.infrastructure.terminal.commands.dto;

import com.danielmasegosa.application.commands.PostCommand;

import java.util.Objects;

public final class PostMessageDto {
    private final String userName;
    private final String postMessage;

    public PostMessageDto(String userName, String postMessage) {
        this.userName = userName;
        this.postMessage = postMessage;
    }

    public String getUserName() {
        return userName;
    }

    public String getPostMessage() {
        return postMessage;
    }

    public PostCommand toDomain() {
        return new PostCommand(this.userName.trim(), this.postMessage.trim());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostMessageDto that = (PostMessageDto) o;
        return Objects.equals(userName, that.userName) && Objects.equals(postMessage, that.postMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, postMessage);
    }

    @Override
    public String toString() {
        return "PostMessageDto{" +
                "userName='" + userName + '\'' +
                ", postMessage='" + postMessage + '\'' +
                '}';
    }
}
