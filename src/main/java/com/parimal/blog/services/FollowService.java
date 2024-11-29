package com.parimal.blog.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.parimal.blog.payloads.FollowRequestResponse;

import java.util.List;
import java.util.Set;

public interface FollowService {

    // Save a new follow request
    void saveFollowRequest(JsonNode activity);

    // Fetch all follow requests where the user is the followee
    List<FollowRequestResponse> getFollowRequestsForUser(String name);

    // Fetch all follow requests for a user (both follower and followee)
    List<FollowRequestResponse> getAllRequestsForUser(String name);

    void acceptFollowRequest(Long requestId);

    Set<JsonNode> getFollowersActors(String username);

    Set<JsonNode> getFollowingActors(String username);

    void addFollower(String username, JsonNode actor);

    void addFollowing(String username, JsonNode actor);

    // Generate a follow URL for a given username
    String generateFollowUrl(String username);

    List<String> getFollowers(String actor);
}
