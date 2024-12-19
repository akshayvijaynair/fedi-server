package com.parimal.blog.controllers;

import com.parimal.blog.entities.Activity;
import com.parimal.blog.entities.PostRequest;
import com.parimal.blog.services.ActivityService;
import com.parimal.blog.services.OutboxService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private OutboxService outboxService;

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody PostRequest postRequest) {
        // Validate input
        if (postRequest.getActor() == null || postRequest.getContent() == null) {
            return ResponseEntity.badRequest().body("Actor and content are required.");
        }

        // Create the activity
        Activity activity = activityService.createActivity(postRequest.getActor(), postRequest.getContent());

        // Save to outbox and propagate to followers
        outboxService.postToOutbox(activity);

        return ResponseEntity.ok("Post created successfully.");
    }
}

