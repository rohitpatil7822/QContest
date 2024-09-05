package com.crio.contest.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User
 */
@Document(collection="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @NotNull(message = "User ID cannot be null")
    private Long userId;
    @NotEmpty(message = "User Name cannot be blank")
    private String userName;
    private int score = 0;
    private Set<String> badges = new HashSet<>();

    public void assignBadges() {

        if (score == 0 && badges.isEmpty()) {
            badges.clear();
        }
        
        if (score >= 1 && score < 30) {
            badges.add("Code Ninja");
        } else if (score >= 30 && score < 60) {
            badges.add("Code Champ");
        } else if (score >= 60 && score <= 100) {
            badges.add("Code Master");
        }
    }

    @Override
    public String toString() {
        return "User{" +
            "userId=" + userId +
            ", userName='" + userName + '\'' +
            ", score=" + score +
            ", badges=" + badges +
            '}';
    }
    
}