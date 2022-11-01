package com.chetan.ch4.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

	private AuthenticationProvider authenticationProvider;

	@Autowired
	public ProjectConfig(@Lazy AuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;

		// So if you have a class A and you somehow run into this problem when injecting
		// the class A into the constructor of class B. Use the @Lazy annotation in the
		// constructor of class B. This will break the cycle and inject the bean of A
		// lazily into B. So, instead of fully initializing the bean, it will create a
		// proxy to inject it into the other bean. The injected bean will only be fully
		// created when it’s first needed
	}

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {

		var uds = new InMemoryUserDetailsManager();

		User user = (User) User.withUsername("shiv").password("1234").authorities("read").build();

		uds.createUser(user);
		return uds;
	}

	@Bean
	public NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider);
	}
}
