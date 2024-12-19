package com.parimal.blog.repositories;

import com.parimal.blog.entities.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface FollowRepo extends JpaRepository<Follow, Long> {

    @Query("SELECT f.follower FROM Follow f WHERE f.followee = :actor")
    List<String> findFollowersByFollowee(String actor);

    @Query("SELECT f.followee FROM Follow f WHERE f.follower = :currentUser")
    List<String> findFolloweesByFollower(String currentUser);

    @Query("SELECT a.name, a.following FROM Account a WHERE a.name = :name")
    List<Object[]> findNameAndFollowingByName(String name);

}

