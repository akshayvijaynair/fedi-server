package com.parimal.blog.services;

import com.parimal.blog.entities.Activity;

public interface InboxService {
    void deliverToInbox(String userId, Activity activity);
}

