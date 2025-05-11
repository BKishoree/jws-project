package com.kishore.jws.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kishore.jws.project.repository.UserRepository;
import com.kishore.jws.project.service.CustomUserDetailsService;
import com.kishore.jws.project.service.serviceImpl.CustomUserDetailsServiceImpl;

@Configuration
public class ApplicationConfiguration {
	
	private final UserRepository userRepository;
	
	public ApplicationConfiguration(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Bean
	UserDetailsService userDetailsService() {
		return userName -> userRepository.findByEmail(userName)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
	
	@Bean
	CustomUserDetailsService customUserDetailsService(UserRepository userRepository) {
		return new CustomUserDetailsServiceImpl(userRepository);
	}


	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setUserDetailsService(userDetailsService());
		provider.setPasswordEncoder(passwordEncoder());
		
		return provider;
	}

}
