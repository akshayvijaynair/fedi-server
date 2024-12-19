package com.parimal.blog.services.impl;

import com.parimal.blog.entities.Activity;
import com.parimal.blog.entities.Inbox;
import com.parimal.blog.repositories.InboxRepo;
import com.parimal.blog.services.InboxService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Service
public class InboxServiceImpl implements InboxService {

    @Autowired
    private InboxRepo inboxRepository;

    @Override
    public void deliverToInbox(String userId, Activity activity) {
        Inbox inboxEntry = new Inbox();
        inboxEntry.setUserId(userId);
        inboxEntry.setActivityId(activity.getId());
        inboxEntry.setReceived(LocalDateTime.now());
        inboxRepository.save(inboxEntry);
    }
}

