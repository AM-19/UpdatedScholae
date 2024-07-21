package com.stackroute.bookservice.Model;


/*
For the Storing notes on a particular books
 */

//import org.springframework.data.mongodb.core.index.Indexed;

public class Note {
    //@Indexed(unique = true)
    private String userEmail;
    private String noteTitle;
    private String noteContent;
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getNoteTitle() {
		return noteTitle;
	}
	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}
	public String getNoteContent() {
		return noteContent;
	}
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}
    
    
}
