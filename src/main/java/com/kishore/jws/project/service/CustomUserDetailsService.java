package com.kishore.jws.project.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailsService extends UserDetailsService {

	CustomUserDetails loadUserByEmail(String email) throws UsernameNotFoundException;
}
