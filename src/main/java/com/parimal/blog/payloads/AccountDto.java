package com.parimal.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDto {

    private String id; // Unique identifier (actor URL in ActivityPub)
    private String name; // Display name of the user
    private String actorUrl; // ActivityPub compliant actor URL

}
