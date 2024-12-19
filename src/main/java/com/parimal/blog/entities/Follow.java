package com.parimal.blog.entities;

import javax.persistence.*;

@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String follower; // URI of the follower
    private String followee; // URI of the followee

    // Getters and setters
}

