package com.parimal.blog.services.impl;

import com.parimal.blog.config.AppConstants;
import com.parimal.blog.repositories.AccountRepo;
import com.parimal.blog.services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private AccountRepo accountRepo;

    @Override
    public void sendFollowRequest(String actor, String object) {
        String inboxUrl = getInboxUrl(object);

        Map<String, Object> followActivity = new HashMap<>();
        followActivity.put("@context", "https://www.w3.org/ns/activitystreams");
        followActivity.put("type", "Follow");
        followActivity.put("actor", actor);
        followActivity.put("object", object);

        // Logic to send the follow activity to the target user's inbox
        // e.g., using an HTTP client to send a POST request
    }

    @Override
    public String generateFollowUrl(String username) {
        String followUrl = "https://" + AppConstants.DOMAIN + "/activitypub/follow/" + username;
        System.out.println("Generated follow URL in FollowService: " + followUrl);
        return followUrl;
    }


    private String getInboxUrl(String actor) {
        return accountRepo.findInboxByActor(actor); // Implement this method in AccountRepo
    }
}
