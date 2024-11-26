package com.parimal.blog.controllers;

import com.parimal.blog.services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/request")
    public ResponseEntity<String> sendFollowRequest(@RequestParam Long followerId, @RequestParam Long followeeId) {
        followService.sendFollowRequest(followerId, followeeId);
        return ResponseEntity.ok("Follow request sent successfully.");
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getPendingFollowRequests(@RequestParam Long accountId) {
        return ResponseEntity.ok(followService.getPendingFollowRequests(accountId));
    }

    @PostMapping("/response")
    public ResponseEntity<String> handleFollowResponse(
            @RequestParam Long followRequestId,
            @RequestParam boolean accept) {
        followService.handleFollowResponse(followRequestId, accept);
        return ResponseEntity.ok(accept ? "Follow request accepted." : "Follow request rejected.");
    }
}
