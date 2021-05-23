package com.danielmasegosa.infrastructure.terminal.commands.dto;

import java.util.Objects;

public final class RetrieveMessagesDto {

    private String userName;

    public RetrieveMessagesDto(final String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetrieveMessagesDto that = (RetrieveMessagesDto) o;
        return Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }

    @Override
    public String toString() {
        return "RetrieveMessagesDto{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
