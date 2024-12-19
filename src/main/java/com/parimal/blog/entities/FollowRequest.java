package com.parimal.blog.entities;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "follow_requests")
public class FollowRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private Account follower; // Account initiating the follow request

    @ManyToOne
    @JoinColumn(name = "followee_id", nullable = false)
    private Account followee; // Account being followed

    @Column(name = "status", nullable = false)
    private String status; // PENDING, ACCEPTED, REJECTED

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
