package com.danielmasegosa.infrastructure.terminal.commands.dto;

import com.danielmasegosa.infrastructure.terminal.commands.dto.validation.Notification;

import java.util.Objects;

public final class SubscribtioDto {
    private final String userName;
    private final String subscribeTo;

    public SubscribtioDto(final String userName, final String subscribeTo) {
        this.userName = userName;
        this.subscribeTo = subscribeTo;
    }

    public Notification check() {
        final Notification note = new Notification();
        if (userName.isBlank()){
            note.addError("Username cannot be empty");
        }
        if (subscribeTo.isBlank()){
            note.addError("Followee username cannot be empty");
        }
        return note;
    }

    public String getUserName() {
        return userName;
    }

    public String getSubscribeTo() {
        return subscribeTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscribtioDto that = (SubscribtioDto) o;
        return Objects.equals(userName, that.userName) && Objects.equals(subscribeTo, that.subscribeTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, subscribeTo);
    }

    @Override
    public String toString() {
        return "SubscribeMessagesDto{" +
                "userName='" + userName + '\'' +
                ", subscribeTo='" + subscribeTo + '\'' +
                '}';
    }
}
