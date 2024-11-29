package com.parimal.blog.services.impl;

import com.parimal.blog.entities.Account;
import com.parimal.blog.entities.Activity;
import com.parimal.blog.repositories.AccountRepo;
import com.parimal.blog.repositories.ActivityRepo;
import com.parimal.blog.repositories.FollowRepo;
import com.parimal.blog.services.FeedService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private FollowRepo followRepository;

    @Autowired
    private ActivityRepo activityRepository;

    @Autowired
    private AccountRepo accountRepository;

    @Transactional
    @Override
    public List<Activity> getFeedForUser(String currentUser) {
        Account account = accountRepository.findByName(currentUser)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + currentUser));
        Set<String> followingIds = account.getFollowingIds();
        return activityRepository.findActivitiesByActorsAndType(followingIds, "Create");
    }


}
