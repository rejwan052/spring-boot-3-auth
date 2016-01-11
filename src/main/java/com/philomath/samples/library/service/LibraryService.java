package com.philomath.samples.library.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.philomath.samples.library.entity.LibraryBook;
import com.philomath.samples.library.entity.LibraryUser;
import com.philomath.samples.library.repo.BookRepository;
import com.philomath.samples.library.repo.UserRepository;
import com.philomath.samples.library.util.LibraryException;

@Component
public class LibraryService implements ILibraryService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BookRepository bookRepo;
	
	public Long addBook(Map<String, Object> book)	throws LibraryException	{
		LibraryBook libBook = new LibraryBook();
		libBook.setBookName((String)book.get("name"));
		libBook.setCategory((String)book.get("category"));
		libBook.setIsbn((String)book.get("isbn"));
		String[] authors = ((String)book.get("author")).split(" ");
		libBook.setAuthorFirstName(authors[0]);
		libBook.setAuthorLastName(authors[1]);
		
		libBook = bookRepo.save(libBook);
		
		return libBook.getBookId();
	}
	
	public List<LibraryBook> searchBook(Map<String, Object> searchParams) throws LibraryException	{
		return bookRepo.findByBookNameContainingIgnoreCase((String)(searchParams.get("bookname")));
	}

	public LibraryUser getUserByUsername(String userName) throws LibraryException	{
		LibraryUser user = userRepo.findTopByUserName(userName);
		return user;
	}
	
	@Override
	public List<String> getRole() throws LibraryException {
		Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		Iterator<? extends GrantedAuthority> it = roles.iterator();
		List<String> strRoles = new ArrayList<String>();
		
		while (it.hasNext())	{
			strRoles.add(it.next().getAuthority());
		}
		
		return strRoles;
	}
}
