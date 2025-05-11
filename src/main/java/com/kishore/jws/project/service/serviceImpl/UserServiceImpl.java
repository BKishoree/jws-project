package com.kishore.jws.project.service.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kishore.jws.project.entity.User;
import com.kishore.jws.project.repository.UserRepository;
import com.kishore.jws.project.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}


	@Override
	public List<User> allUsers() {
		try {
			List<User> users = new ArrayList<User>();
			userRepository.findAll().forEach(users::add);
			return users;
		} catch(Exception exception) {
			return Collections.emptyList();
		}
	}

}
