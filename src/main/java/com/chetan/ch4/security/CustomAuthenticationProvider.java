package com.chetan.ch4.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsService userDetails;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		// here you implement the authentication logic

		// if the request is authentication you should return here
		// an fully authenticated Authentication instance

		// if the request is not authenticated you should throw AuthenticationException

		// the Authentication isn't supported by this AP -> return null

		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		UserDetails user = userDetails.loadUserByUsername(username);
		if (user != null) {
			if (passwordEncoder.matches(password, user.getPassword())) {
				return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
			}
		}
		
		throw new BadCredentialsException("Error!");
	}

	@Override
	public boolean supports(Class<?> authenticationType) {

		return UsernamePasswordAuthenticationToken.class.equals(authenticationType);
	}

}
