package com.parimal.blog.entities;

import com.fasterxml.jackson.databind.JsonNode;
import javax.persistence.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

@Entity
@Table(name = "accounts")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonBinaryType.class)
})
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Type(type = "json")
    @Column(name = "actor", columnDefinition = "jsonb", nullable = false)
    private JsonNode actor;

    @Type(type = "json")
    @Column(name = "pubkey", columnDefinition = "jsonb", nullable = false)
    private JsonNode pubkey;

    @Type(type = "json")
    @Column(name = "privkey", columnDefinition = "jsonb", nullable = false)
    private JsonNode privkey;

    @Type(type = "json")
    @Column(name = "webfinger", columnDefinition = "jsonb", nullable = false)
    private JsonNode webfinger;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "icon", columnDefinition = "TEXT")
    private String icon;

    @Column(name = "followers", columnDefinition = "TEXT")
    private String followers;

    @Column(name = "following", columnDefinition = "TEXT")
    private String following;

    @Column(name = "liked", columnDefinition = "TEXT")
    private String liked;

    @Column(name = "endpoints", columnDefinition = "TEXT")
    private String endpoints;

    @Column(name = "follow_endpoint", nullable = true, columnDefinition = "TEXT")
    private String followUrl;

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

    public JsonNode getActor() {
        return actor;
    }

    public void setActor(JsonNode actor) {
        this.actor = actor;
    }

    public JsonNode getPubkey() {
        return pubkey;
    }

    public void setPubkey(JsonNode pubkey) {
        this.pubkey = pubkey;
    }

    public JsonNode getPrivkey() {
        return privkey;
    }

    public void setPrivkey(JsonNode privkey) {
        this.privkey = privkey;
    }

    public JsonNode getWebfinger() {
        return webfinger;
    }

    public void setWebfinger(JsonNode webfinger) {
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

    public String getFollowUrl() {
        return followUrl;
    }

    public void setFollowUrl(String followUrl) {
        this.followUrl = followUrl;
    }

}
