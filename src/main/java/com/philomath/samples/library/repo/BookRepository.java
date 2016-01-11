package com.philomath.samples.library.repo;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.philomath.samples.library.entity.LibraryBook;

public interface BookRepository extends Repository<LibraryBook, Long> {

	List<LibraryBook> findByBookNameContainingIgnoreCase(String bookName);
	
	List<LibraryBook> findByIsbn(String isbn);
	
	LibraryBook save(LibraryBook libraryBook);
	
}	
