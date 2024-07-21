package com.stackroute.bookservice.Model;


import org.springframework.data.annotation.Id;
/*
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
*/
import java.util.List;


public class BookNotes {
    @Id
    String bookTitle;
    List<Note> notes;

	public BookNotes() {
	}

	public BookNotes(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public BookNotes(String bookTitle, List<Note> notes) {
		this.bookTitle = bookTitle;
		this.notes = notes;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
}
