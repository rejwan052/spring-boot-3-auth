package com.philomath.samples.library.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LIBRARY_BOOK")
public class LibraryBook {

	@Id
	@Column(name="BOOK_ID", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookId;
	
	@Column(name="BOOK_NAME", nullable=false)
	private String bookName;
	
	@Column(name="AUTHOR_FIRST_NAME")
	private String authorFirstName;
	
	@Column(name="AUTHOR_LAST_NAME", nullable=false)
	private String authorLastName;
	
	@Column(name="ISBN", nullable=false)
	private String isbn;
	
	@Column(name="CATEGORY")
	private String category;

	public LibraryBook()	{
		
	}
	public LibraryBook(Long bookId, String bookName, String authorFirstName,
			String authorLastName, String isbn, String category) {
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.authorFirstName = authorFirstName;
		this.authorLastName = authorLastName;
		this.isbn = isbn;
		this.category = category;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthorFirstName() {
		return authorFirstName;
	}

	public void setAuthorFirstName(String authorFirstName) {
		this.authorFirstName = authorFirstName;
	}

	public String getAuthorLastName() {
		return authorLastName;
	}

	public void setAuthorLastName(String authorLastName) {
		this.authorLastName = authorLastName;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
	
}
