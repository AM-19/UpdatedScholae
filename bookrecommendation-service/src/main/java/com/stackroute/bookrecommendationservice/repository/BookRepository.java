package com.stackroute.bookrecommendationservice.repository;

import com.stackroute.bookrecommendationservice.model.Book;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends Neo4jRepository<Book, String> {

    @Query("MATCH(b:Book {bookTitle:$bookTitle}) RETURN b")
    List<Book> findBooksByBookTitle(String bookTitle);

    //@Query("MATCH(b:Book {bookTitle:$bookTitle}),(a:Author {authorName:$authorName}) CREATE(b)-[:authored_by]->(a)")
    @Query("MATCH (b:Book {bookTitle:$bookTitle}) MATCH (a:Author {authorName:$authorName}) CREATE (b)-[:authored_by]->(a)")
    void createAuthoredByRelationship(String bookTitle,String authorName);
}
