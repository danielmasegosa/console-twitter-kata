package com.danielmasegosa.domain.exceptions;

public final class UserNotFound extends RuntimeException {

    public UserNotFound(final String user) {
        super(user + " not found");
    }
}
