package com.parimal.blog.services;

import com.parimal.blog.entities.Activity;

import java.util.List;

public interface FeedService {

    List<Activity> getFeedForUser(String currentUser);
}

