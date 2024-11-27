package com.parimal.blog.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.parimal.blog.payloads.FollowRequestResponse;

import java.util.List;

public interface FollowService {

    // Save a new follow request
    void saveFollowRequest(JsonNode activity);

    // Fetch all follow requests where the user is the followee
    List<FollowRequestResponse> getFollowRequestsForUser(String name);

    // Fetch all follow requests for a user (both follower and followee)
    List<FollowRequestResponse> getAllRequestsForUser(String name);

    // Generate a follow URL for a given username
    String generateFollowUrl(String username);
}
