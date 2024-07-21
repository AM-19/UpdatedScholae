package com.stackroute.bookrecommendationservice.repository;

import com.stackroute.bookrecommendationservice.model.Author;
import com.stackroute.bookrecommendationservice.model.Book;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuthorRepository extends Neo4jRepository<Author, String> {
    @Query("MATCH(a:Author {authorName:$authorName}) RETURN a")
    List<Author> findAuthorsByAuthorName(String authorName);
}
