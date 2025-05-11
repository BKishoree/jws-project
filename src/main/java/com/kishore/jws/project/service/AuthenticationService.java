package com.kishore.jws.project.service;

import com.kishore.jws.project.entity.User;
import com.kishore.jws.project.model.request.LoginRequestDto;
import com.kishore.jws.project.model.request.SignInRequestDto;
import com.kishore.jws.project.model.request.VerifyUserRequestDto;

public interface AuthenticationService {

	User signIn(SignInRequestDto signInRequestDto);
	
	User login(LoginRequestDto loginRequestDto);
	
	void verifyUser(VerifyUserRequestDto verifyUserRequestDto);
	
	void resendVerficationCode(String email);
}
