package com.parimal.blog.services.impl;

import com.parimal.blog.entities.Activity;
import com.parimal.blog.entities.Outbox;
import com.parimal.blog.repositories.OutboxRepo;
import com.parimal.blog.services.FollowService;
import com.parimal.blog.services.InboxService;
import com.parimal.blog.services.OutboxService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class OutboxServiceImpl implements OutboxService {

    @Autowired
    private OutboxRepo outboxRepository;

    @Autowired
    private InboxService inboxService;

    @Autowired
    private FollowService followService;

    @Override
    public void postToOutbox(Activity activity) {
        // Save the activity to the actor's outbox
        Outbox outboxEntry = new Outbox();
        outboxEntry.setActor(activity.getActor());
        outboxEntry.setActivityId(activity.getId());
        outboxEntry.setPublished(activity.getPublished());
        outboxRepository.save(outboxEntry);

        // Deliver activity to followers' inboxes
        List<String> followers = followService.getFollowers(activity.getActor());
        for (String follower : followers) {
            inboxService.deliverToInbox(follower, activity);
        }
    }
}
