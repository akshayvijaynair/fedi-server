package com.parimal.blog.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.parimal.blog.payloads.FollowRequestResponse;
import com.parimal.blog.entities.Account;
import com.parimal.blog.entities.FollowRequest;
import com.parimal.blog.repositories.AccountRepo;
import com.parimal.blog.repositories.FollowRepo;
import com.parimal.blog.repositories.FollowRequestRepo;
import com.parimal.blog.services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private FollowRequestRepo followRequestRepo;

    @Autowired
    private FollowRepo followRepository;

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

        followRequestRepo.save(followRequest);
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
    @Transactional
    public void acceptFollowRequest(Long requestId) {
        FollowRequest followRequest = followRequestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Follow request not found."));

        if (!"PENDING".equalsIgnoreCase(followRequest.getStatus())) {
            throw new RuntimeException("Follow request is not in a pending state.");
        }

        Account follower = followRequest.getFollower();
        Account followee = followRequest.getFollowee();

        // Add actor of the follower to followee's followers
        followee.getFollowers().add(follower.getActor());
        // Add actor of the followee to follower's following
        follower.getFollowing().add(followee.getActor());

        accountRepo.save(follower);
        accountRepo.save(followee);

        followRequestRepo.delete(followRequest);
    }

    @Override
    public Set<JsonNode> getFollowersActors(String username) {
        Account account = accountRepo.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found."));
        return account.getFollowers();
    }

    @Override
    public Set<JsonNode> getFollowingActors(String username) {
        Account account = accountRepo.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found."));
        return account.getFollowing();
    }

    @Override
    public void addFollower(String username, JsonNode actor) {
        Account account = accountRepo.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found."));
        account.getFollowers().add(actor);
        accountRepo.save(account);
    }

    @Override
    public void addFollowing(String username, JsonNode actor) {
        Account account = accountRepo.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found."));
        account.getFollowing().add(actor);
        accountRepo.save(account);
    }

    @Override
    public String generateFollowUrl(String username) {
        return "https://example.com/activitypub/follow/" + username;
    }

    @Override
    public List<String> getFollowers(String actor) {
        return followRepository.findFollowersByFollowee(actor);
    }
}
