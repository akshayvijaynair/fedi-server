package com.parimal.blog.services;

import com.parimal.blog.entities.Activity;

public interface ActivityService {
    Activity createActivity(String actor, String content);
}


