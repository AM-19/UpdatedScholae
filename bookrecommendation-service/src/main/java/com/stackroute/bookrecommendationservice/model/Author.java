package com.stackroute.bookrecommendationservice.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.UUID;

@Node("Author")
public class Author {
    @Id @GeneratedValue(UUIDStringGenerator.class)
    private String authorId;
    @Property("authorName")
    private String authorName;

    public String getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                '}';
    }

    public Author(String authorName) {
        this.authorName = authorName;
    }
}
