package com.kishore.jws.project.service.serviceImpl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kishore.jws.project.repository.UserRepository;
import com.kishore.jws.project.service.CustomUserDetails;
import com.kishore.jws.project.service.CustomUserDetailsService;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
	
	private final UserRepository userRepository;

	public CustomUserDetailsServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return loadUserByEmail(username);
	}

	@Override
	public CustomUserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
		return (CustomUserDetails) userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

}
