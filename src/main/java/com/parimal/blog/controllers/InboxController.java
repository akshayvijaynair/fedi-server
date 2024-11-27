package com.parimal.blog.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.parimal.blog.entities.FollowRequest;
import com.parimal.blog.payloads.AccountDto;
import com.parimal.blog.payloads.FollowRequestResponse;
import com.parimal.blog.services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/users")
public class InboxController {

    @Autowired
    private FollowService followService;

    @PostMapping("/{email}/inbox")
    public ResponseEntity<String> handleInboxActivity(@PathVariable String email, @RequestBody JsonNode activity) {
        String type = activity.get("type").asText();

        if ("Follow".equalsIgnoreCase(type)) {
            followService.saveFollowRequest(activity);
            return ResponseEntity.ok("Follow request saved for " + email);
        }

        return ResponseEntity.badRequest().body("Unsupported activity type: " + type);
    }

    @GetMapping("/{name}/inbox")
    public ResponseEntity<List<FollowRequestResponse>> getInboxRequests(@PathVariable String name) {
        List<FollowRequestResponse> followRequests = followService.getAllRequestsForUser(name);
        return ResponseEntity.ok(followRequests);
    }

    @PostMapping("/{email}/inbox/{requestId}/accept")
    public ResponseEntity<String> acceptFollowRequest(@PathVariable String email, @PathVariable Long requestId) {
        try {
            followService.acceptFollowRequest(requestId);
            return ResponseEntity.ok("Follow request accepted and removed.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{username}/followers")
    public ResponseEntity<Set<JsonNode>> getFollowers(@PathVariable String username) {
        return ResponseEntity.ok(followService.getFollowersActors(username));
    }

    @GetMapping("/{username}/following")
    public ResponseEntity<Set<JsonNode>> getFollowing(@PathVariable String username) {
        return ResponseEntity.ok(followService.getFollowingActors(username));
    }


}
