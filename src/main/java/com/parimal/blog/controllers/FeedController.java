package com.parimal.blog.controllers;

import com.parimal.blog.entities.Activity;
import com.parimal.blog.services.FeedService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private FeedService feedService;

    @GetMapping("/user/{currentUser}")
    public ResponseEntity<List<Activity>> getUserFeed(@PathVariable String currentUser) {
        // Fetch the feed for the current user
        List<Activity> feed = feedService.getFeedForUser(currentUser);

        return ResponseEntity.ok(feed);
    }
}

