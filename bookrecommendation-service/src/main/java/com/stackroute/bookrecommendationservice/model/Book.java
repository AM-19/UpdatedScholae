package com.stackroute.bookrecommendationservice.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Node("Book")
public class Book {
    @Id @GeneratedValue(UUIDStringGenerator.class)
    private String id;
//    @Relationship("authored_by")
//    private Author author;
    @Property("bookTitle")
    private String bookTitle;

    @Property("authorName")
    private String authorName;

    @Property("description")
    private String description;

    @Property("bookUrl")
    private String bookUrl;

    @Property("totalViews")
    private Long totalViews;

    @Property("totalPage")
    private Integer totalPage;

    @Property("publisher")
    private String publisher;

    @Property("totalDownloads")
    private Long totalDownloads;

    @Property("imageUrl")
    private String imageUrl;

    @Property("isbnNumber")
    private String isbnNumber;

    @Property("genre")
    private String genre;
	
    public Book() {
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getId() {
        return id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getDescription() {
        return description;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public Long getTotalViews() {
        return totalViews;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public String getPublisher() {
        return publisher;
    }

    public Long getTotalDownloads() {
        return totalDownloads;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getIsbnNumber() {
        return isbnNumber;
    }

    public String getGenre() {
		return genre;
	}

    public void setId(String id) {
        this.id = id;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public void setTotalViews(Long totalViews) {
        this.totalViews = totalViews;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setTotalDownloads(Long totalDownloads) {
        this.totalDownloads = totalDownloads;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setIsbnNumber(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookTitle='" + bookTitle + '\'' +
                ", authorName='" + authorName + '\'' +
                ", description='" + description + '\'' +
                ", bookUrl='" + bookUrl + '\'' +
                ", totalViews=" + totalViews +
                ", totalPage=" + totalPage +
                ", publisher='" + publisher + '\'' +
                ", totalDownloads=" + totalDownloads +
                ", imageUrl='" + imageUrl + '\'' +
                ", isbnNumber='" + isbnNumber + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }

    public Book(String bookTitle, String authorName, String description, String bookUrl, Long totalViews, int totalPage, String publisher, long totalDownloads, String imageUrl, String isbnNumber, String genre) {
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.description = description;
        this.bookUrl = bookUrl;
        this.totalViews = totalViews;
        this.totalPage = totalPage;
        this.publisher = publisher;
        this.totalDownloads = totalDownloads;
        this.imageUrl = imageUrl;
        this.isbnNumber = isbnNumber;
		this.genre = genre;
    }
}