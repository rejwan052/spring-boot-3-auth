package com.philomath.samples.library.repo;

import java.util.List;

import org.springframework.data.repository.*;

import com.philomath.samples.library.entity.LibraryUser;

public interface UserRepository extends Repository<LibraryUser, Long> {
	
	List<LibraryUser> findAllByUserName(String userName);
	
	LibraryUser findTopByUserName(String userName);

}
