package com.danielmasegosa.infrastructure.terminal.commands.dto;

import com.danielmasegosa.infrastructure.terminal.commands.dto.validation.Notification;

import java.util.Objects;

public final class RequestWallDto {
    private String userName;

    public RequestWallDto(final String userName) {
        this.userName = userName;
    }

    public Notification check() {
        final Notification note = new Notification();
        if (userName.isBlank()){
            note.addError("Username cannot be empty");
        }
        return note;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestWallDto that = (RequestWallDto) o;
        return Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }

    @Override
    public String toString() {
        return "RequestWallDto{" +
                "aUserName='" + userName + '\'' +
                '}';
    }
}
