package com.parimal.blog.entities;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "actor", columnDefinition = "TEXT", nullable = false)
    private String actor; // JSON string of the Actor data

    @Column(name = "pubkey", columnDefinition = "TEXT", nullable = false)
    private String pubkey;

    @Column(name = "privkey", columnDefinition = "TEXT", nullable = false)
    private String privkey;

    @Column(name = "webfinger", columnDefinition = "TEXT", nullable = false)
    private String webfinger;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "icon", columnDefinition = "TEXT")
    private String icon; // JSON string if storing additional icon metadata

    @Column(name = "followers", columnDefinition = "TEXT")
    private String followers;

    @Column(name = "following", columnDefinition = "TEXT")
    private String following;

    @Column(name = "liked", columnDefinition = "TEXT")
    private String liked;

    @Column(name = "endpoints", columnDefinition = "TEXT")
    private String endpoints; // JSON string of endpoints, e.g., shared inbox

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getPubkey() {
        return pubkey;
    }

    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }

    public String getPrivkey() {
        return privkey;
    }

    public void setPrivkey(String privkey) {
        this.privkey = privkey;
    }

    public String getWebfinger() {
        return webfinger;
    }

    public void setWebfinger(String webfinger) {
        this.webfinger = webfinger;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public String getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(String endpoints) {
        this.endpoints = endpoints;
    }
}

