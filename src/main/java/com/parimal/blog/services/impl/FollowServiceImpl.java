package com.parimal.blog.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.parimal.blog.entities.Account;
import com.parimal.blog.entities.FollowRequest;
import com.parimal.blog.repositories.AccountRepo;
import com.parimal.blog.repositories.FollowRequestRepo;
import com.parimal.blog.services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private FollowRequestRepo followRequestRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public void sendFollowRequest(Long followerId, Long followeeId) {
        // Validation and existing request checks

        // Retrieve follower and followee accounts
        Account follower = accountRepo.findById(followerId).orElseThrow(() -> new RuntimeException("Follower not found."));
        Account followee = accountRepo.findById(followeeId).orElseThrow(() -> new RuntimeException("Followee not found."));

        // Build the follow activity
        JsonNode followActivity = buildFollowRequestActivity(follower, followee);

        // Send activity to the followee's inbox
        String inboxUrl = followee.getActor().get("inbox").asText();
        sendActivityToInbox(inboxUrl, followActivity);

        // Save the follow request in the database
        FollowRequest followRequest = new FollowRequest();
        followRequest.setFollower(follower);
        followRequest.setFollowee(followee);
        followRequest.setStatus("PENDING");
        followRequest.setCreatedAt(LocalDateTime.now());
        followRequest.setUpdatedAt(LocalDateTime.now());
        followRequestRepo.save(followRequest);
    }


    @Override
    @Transactional
    public void handleFollowResponse(Long followRequestId, boolean accept) {
        FollowRequest followRequest = followRequestRepo.findById(followRequestId)
                .orElseThrow(() -> new RuntimeException("Follow request not found."));

        if (!followRequest.getStatus().equals("PENDING")) {
            throw new IllegalStateException("Follow request is not pending.");
        }

        followRequest.setStatus(accept ? "ACCEPTED" : "REJECTED");
        followRequest.setUpdatedAt(LocalDateTime.now());
        followRequestRepo.save(followRequest);

        if (accept) {
            Account follower = followRequest.getFollower();
            Account followee = followRequest.getFollowee();

            JsonNode acceptActivity = buildResponseActivity("Accept", followee, follower);
            sendActivityToInbox(follower.getActor().get("inbox").asText(), acceptActivity);
        }
    }

    @Override
    public List<FollowRequest> getPendingFollowRequests(Long accountId) {
        return followRequestRepo.findByFolloweeIdAndStatus(accountId, "PENDING");
    }

    @Override
    public String generateFollowUrl(String username) {
        return "https://example.com/activitypub/follow/" + username;
    }

    private JsonNode buildFollowRequestActivity(Account follower, Account followee) {
        ObjectNode activity = objectMapper.createObjectNode();
        activity.put("@context", "https://www.w3.org/ns/activitystreams");
        activity.put("type", "Follow");
        activity.put("actor", follower.getActor().get("id").asText());
        activity.put("object", followee.getActor().get("id").asText());
        activity.put("summary", "User " + follower.getName() + " wants to follow " + followee.getName());

        // Additional metadata for followee to respond
        ObjectNode meta = activity.putObject("meta");
        meta.put("actionAccept", "/api/follow/accept?followerId=" + follower.getId() + "&followeeId=" + followee.getId());
        meta.put("actionReject", "/api/follow/reject?followerId=" + follower.getId() + "&followeeId=" + followee.getId());

        return activity;
    }


    private JsonNode buildResponseActivity(String type, Account targetAccount, Account followerAccount) {
        return objectMapper.createObjectNode()
                .put("@context", "https://www.w3.org/ns/activitystreams")
                .put("type", type)
                .put("actor", targetAccount.getActor().get("id").asText())
                .put("object", followerAccount.getActor().get("id").asText());
    }

    private void sendActivityToInbox(String inboxUrl, JsonNode activity) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0M0BnbWFpbC5jb20iLCJleHAiOjE3MzI2NjQxNDUsImlhdCI6MTczMjY0NjE0NX0.c3oO2OuH8Y6JTA6lzZ-hD8OA8OftAcJAenXfzLTDSCCD-3F4qITt4rFaSGRfuBufPue1TIAv78GoZGdhGWquwg");

        HttpEntity<String> requestEntity = new HttpEntity<>(activity.toString(), headers);

        try {
            System.out.println("Sending activity to inbox: " + inboxUrl);
            restTemplate.postForObject(inboxUrl, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            System.err.println("HTTP Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw new RuntimeException("Failed to send activity to inbox. HTTP Status: " + e.getStatusCode(), e);
        } catch (Exception e) {
            System.err.println("Error sending activity: " + e.getMessage());
            throw new RuntimeException("Failed to send activity to inbox.", e);
        }
    }


}
