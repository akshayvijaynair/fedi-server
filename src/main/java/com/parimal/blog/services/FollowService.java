package com.parimal.blog.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.parimal.blog.entities.FollowRequest;

import java.util.List;

public interface FollowService {

    void sendFollowRequest(Long followerId, Long followeeId);

    void handleFollowResponse(Long followRequestId, boolean accept);

    List<FollowRequest> getPendingFollowRequests(Long accountId);

    String generateFollowUrl(String username);
}
