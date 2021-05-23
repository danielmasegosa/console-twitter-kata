package com.danielmasegosa.domain.repository;

import com.danielmasegosa.domain.Post;
import com.danielmasegosa.domain.User;

import java.util.List;

public interface UserRepository {

    void savePost(final Post post);

    List<Post> retrieveMessageByUser(final User user);

    void saveSubscription(final User aFollower, final User aFollowee);

    List<Post> retrieveUserWall(final User user);
}
