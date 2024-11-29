package com.parimal.blog.services;

import com.parimal.blog.entities.Activity;

public interface OutboxService {
    void postToOutbox(Activity activity);
}

