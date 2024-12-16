package com.parimal.blog.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/users")
public class InboxController {

    private final Map<String, JsonNode> activityStore = new HashMap<>();

    @PostMapping("/{email}/inbox")
    public ResponseEntity<String> handleInboxActivity(@PathVariable String email, @RequestBody JsonNode activity) {
        System.out.println("Activity received for: " + email);
        System.out.println("Activity: " + activity.toString());

        activityStore.put(email, activity);

        return ResponseEntity.ok("Activity successfully received for " + email);
    }

    @GetMapping("/{email}/inbox")
    public ResponseEntity<JsonNode> getInboxActivity(@PathVariable String email) {
        JsonNode activity = activityStore.get(email);
        if (activity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(activity);
    }
}
