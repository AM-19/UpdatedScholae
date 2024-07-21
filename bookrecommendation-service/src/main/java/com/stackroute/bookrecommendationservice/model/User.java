package com.stackroute.bookrecommendationservice.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.List;
import java.util.UUID;

@Node("User")
public class User {
    @Id @GeneratedValue(UUIDStringGenerator.class)
    private String userId;
    @Property("emailId")
    private String emailId;

    List<Book> favoriteBooks;


    public User(String emailId) {
        this.emailId = emailId;
    }

    public User() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Book> getFavoriteBooks() {
        return favoriteBooks;
    }

    public void setFavoriteBooks(List<Book> favoriteBooks) {
        this.favoriteBooks = favoriteBooks;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", emailId='" + emailId + '\'' +
                '}';
    }
}
