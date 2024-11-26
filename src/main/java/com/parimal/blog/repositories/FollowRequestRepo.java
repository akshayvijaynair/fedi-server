package com.parimal.blog.repositories;

import com.parimal.blog.entities.FollowRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRequestRepo extends JpaRepository<FollowRequest, Long> {

    List<FollowRequest> findByFolloweeIdAndStatus(Long followeeId, String status);

    boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
}
