package com.parimal.blog.payloads;

import java.time.LocalDateTime;

public class FollowRequestResponse {

    private String context = "https://www.w3.org/ns/activitystreams";
    private String type = "Follow";
    private String actor; // Follower URL
    private String object; // Followee URL
    private String summary;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public FollowRequestResponse(String actor, String object, String summary, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.actor = actor;
        this.object = object;
        this.summary = summary;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters
    public String getContext() {
        return context;
    }

    public String getType() {
        return type;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
