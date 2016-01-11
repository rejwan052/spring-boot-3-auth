package com.philomath.samples.library.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class LibrarySecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private TokenAuthFilter tokenAuthFilter;
	
	@Autowired
	private TokenAuthProvider tokenAuthProvider;
		
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
        http
        // no session management required
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()        
        .authorizeRequests()
        // the following URLs should be permitted without any authentication
        // this includes our static resources 
        // **Note** : Our landing page ("/") is the login page that 
        //			should not be authenticated, so we add it here  
        .antMatchers("/css/**", "/js/**", "/*.html", "/images/**", "/node_modules/**","/").permitAll()
        // all other requests must be authenticated
        .antMatchers("/search").hasRole("SEARCH_BOOKS")
        .antMatchers("/add").hasRole("ADD_BOOKS")
        .and().authorizeRequests()
        .anyRequest().fullyAuthenticated()
        .and()
        // remove basic HTTP authentication - we are writing our own login page 
        //.httpBasic()
        //.and()
        // disable Cross Site Request Forgery token 
        // we do not rely on cookie based auth and are completely stateless so we are safe
        .csrf().disable()
        // authentication for token based authentication
        .authenticationProvider(tokenAuthProvider)
        .addFilterBefore(tokenAuthFilter, BasicAuthenticationFilter.class);
        
       
    }
	
}
