package com.parimal.blog.services.impl;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.parimal.blog.entities.Activity;
import com.parimal.blog.repositories.ActivityRepo;
import com.parimal.blog.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepo activityRepository;

    @Override
    public Activity createActivity(String actor, String content) {
        // Construct the JSON object for the post
        ObjectNode object = JsonNodeFactory.instance.objectNode();
        object.put("type", "Note");
        object.put("content", content);

        // Create the ActivityPub "Create" activity
        Activity activity = new Activity();
        activity.setActor(actor);
        activity.setType("Create");
        activity.setObject(object.toString());
        activity.setPublished(LocalDateTime.now());

        // Save the activity to the database
        return activityRepository.save(activity);
    }
}

