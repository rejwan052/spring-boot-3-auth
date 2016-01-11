package com.philomath.samples.library.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.philomath.samples.library.entity.LibraryRole;
import com.philomath.samples.library.entity.LibraryUser;
import com.philomath.samples.library.service.ILibraryService;
import com.philomath.samples.library.util.LibraryException;

@Component
public class TokenAuthProvider implements AuthenticationProvider {
	
	@Autowired
	private ILibraryService libraryService;

	 
	@Override
	public Authentication authenticate(Authentication authToken)
			throws AuthenticationException {
		
		// get the token from the authentication object
		String username = authToken.getPrincipal().toString();
		String password = authToken.getCredentials().toString();
		
		// get the user from DB, check credentials and create new token
		LibraryUser user = null;
		UsernamePasswordAuthenticationToken newToken = null;
        
    	try {
    		user = libraryService.getUserByUsername(username);
    	}
    	catch(LibraryException e)	{
    		e.printStackTrace();
    	}
    	
    	if (user == null){
    		throw new BadCredentialsException("Invalid username");
    	}
    	else	{
	    	List<LibraryRole> roles = user.getRoles();
	        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	        for (LibraryRole role : roles)	{
	        	GrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
	        	authorities.add(authority);
	        }
	    	
	        
	        if (password.equals(user.getUserPassword())) {
	        	newToken = new UsernamePasswordAuthenticationToken(username, password, authorities);
	        }
	        else	{
				throw new BadCredentialsException("Invalid password");
			}
    	}
		return newToken;
	}

	@Override
	public boolean supports(Class<?> arg0) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(arg0));

	}

	
}
