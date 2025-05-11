package com.kishore.jws.project.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {

	String getEmail();

}
