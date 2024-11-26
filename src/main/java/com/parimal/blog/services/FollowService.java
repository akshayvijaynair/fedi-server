package com.parimal.blog.services;

public interface FollowService {
    void sendFollowRequest(String actor, String object);
    String generateFollowUrl(String username);
}
