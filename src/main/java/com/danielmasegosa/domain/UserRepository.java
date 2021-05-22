package com.danielmasegosa.domain;

import java.util.Set;

public interface UserRepository {

    void save(final Post aPost);

    Set<Post> retrieveMessageByUser(final User user);

    void subscribeToUser(final User aFollower, final User aFollowee);

    Set<Post> retrieveUserWall(final User user);
}
