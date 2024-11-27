package com.parimal.blog.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.parimal.blog.payloads.FollowRequestResponse;
import com.parimal.blog.entities.Account;
import com.parimal.blog.entities.FollowRequest;
import com.parimal.blog.repositories.AccountRepo;
import com.parimal.blog.repositories.FollowRequestRepo;
import com.parimal.blog.services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private FollowRequestRepo followRequestRepo;

    @Override
    @Transactional
    public void saveFollowRequest(JsonNode activity) {
        String actor = activity.get("actor").asText();
        String object = activity.get("object").asText();

        Account follower = accountRepo.findByActorUrl(actor)
                .orElseThrow(() -> new RuntimeException("Follower account not found."));
        Account followee = accountRepo.findByActorUrl(object)
                .orElseThrow(() -> new RuntimeException("Followee account not found."));

        FollowRequest followRequest = new FollowRequest();
        followRequest.setFollower(follower);
        followRequest.setFollowee(followee);
        followRequest.setStatus("PENDING");
        followRequest.setCreatedAt(LocalDateTime.now());
        followRequest.setUpdatedAt(LocalDateTime.now());

        // Save the follow request
        followRequestRepo.save(followRequest);

        // Optionally, you can add a notification or activity log for the follower here
    }

    @Override
    public List<FollowRequestResponse> getFollowRequestsForUser(String name) {
        Account followee = accountRepo.findByName(name)
                .orElseThrow(() -> new RuntimeException("User account not found."));
        List<FollowRequest> requests = followRequestRepo.findByFolloweeId(followee.getId());

        return requests.stream()
                .map(request -> new FollowRequestResponse(
                        request.getFollower().getActor().get("id").asText(),
                        request.getFollowee().getActor().get("id").asText(),
                        "User " + request.getFollower().getName() + " wants to follow " + request.getFollowee().getName(),
                        request.getStatus(),
                        request.getCreatedAt(),
                        request.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<FollowRequestResponse> getAllRequestsForUser(String name) {
        Account user = accountRepo.findByName(name)
                .orElseThrow(() -> new RuntimeException("User account not found."));

        // Fetch both incoming and outgoing requests
        List<FollowRequest> incomingRequests = followRequestRepo.findByFolloweeId(user.getId());
        List<FollowRequest> outgoingRequests = followRequestRepo.findByFollowerId(user.getId());

        // Combine and transform requests
        return Stream.concat(incomingRequests.stream(), outgoingRequests.stream())
                .map(request -> new FollowRequestResponse(
                        request.getFollower().getActor().get("id").asText(),
                        request.getFollowee().getActor().get("id").asText(),
                        request.getFollower().getId().equals(user.getId())
                                ? "You sent a follow request to " + request.getFollowee().getName()
                                : "User " + request.getFollower().getName() + " wants to follow you",
                        request.getStatus(),
                        request.getCreatedAt(),
                        request.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }


    @Override
    public String generateFollowUrl(String username) {
        return "https://example.com/activitypub/follow/" + username;
    }
}
