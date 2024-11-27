package com.parimal.blog.entities;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

@Entity
@Table(name = "accounts")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonBinaryType.class)
})
@Data
@EqualsAndHashCode
@ToString
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

    @Type(type = "json")
    @Column(name = "followers", columnDefinition = "jsonb", nullable = true)
    private Set<JsonNode> followers = new HashSet<>();

    @Type(type = "json")
    @Column(name = "following", columnDefinition = "jsonb", nullable = true)
    private Set<JsonNode> following = new HashSet<>();

    @Column(name = "liked", columnDefinition = "TEXT")
    private String liked;

    @Column(name = "endpoints", columnDefinition = "TEXT")
    private String endpoints;

    @Column(name = "follow_endpoint", nullable = true, columnDefinition = "TEXT")
    private String followUrl;

    // Utility method to get actor URL from JSON
    public String getActorUrl() {
        return actor != null && actor.has("id") ? actor.get("id").asText() : null;
    }
}
