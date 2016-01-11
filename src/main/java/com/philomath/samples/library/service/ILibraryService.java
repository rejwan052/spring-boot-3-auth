package com.philomath.samples.library.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.philomath.samples.library.entity.LibraryBook;
import com.philomath.samples.library.entity.LibraryUser;
import com.philomath.samples.library.util.LibraryException;

@Component
public interface ILibraryService {
	
	public List<LibraryBook> searchBook(Map<String, Object> searchParams) throws LibraryException;
	
	public Long addBook(Map<String, Object> book)	throws LibraryException;
	
	public List<String> getRole() throws LibraryException;
	
	public LibraryUser getUserByUsername(String userName) throws LibraryException;

}
