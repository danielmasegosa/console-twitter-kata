package com.danielmasegosa.fixtures;

import com.danielmasegosa.domain.User;

public final class UserFixture {

    public static User user = new User("aUserName");

    public static User createUser(final String username) {
        return new User(username);
    }
}
