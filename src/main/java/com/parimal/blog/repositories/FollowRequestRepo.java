package com.parimal.blog.repositories;

import com.parimal.blog.entities.FollowRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRequestRepo extends JpaRepository<FollowRequest, Long> {

    List<FollowRequest> findByFolloweeId(Long followeeId);

    // Fetch requests where the user is the follower
    List<FollowRequest> findByFollowerId(Long followerId);

    // Optional: Fetch all requests for a user
    @Query("SELECT fr FROM FollowRequest fr WHERE fr.follower.id = :userId OR fr.followee.id = :userId")
    List<FollowRequest> findAllRequestsForUser(Long userId);

    // Fetch specific request by follower and followee
    @Query("SELECT fr FROM FollowRequest fr WHERE fr.follower.id = :followerId AND fr.followee.id = :followeeId")
    List<FollowRequest> findByFollowerAndFollowee(Long followerId, Long followeeId);

}
