package com.kishore.jws.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kishore.jws.project.entity.User;
import com.kishore.jws.project.model.request.LoginRequestDto;
import com.kishore.jws.project.model.request.SignInRequestDto;
import com.kishore.jws.project.model.request.VerifyUserRequestDto;
import com.kishore.jws.project.model.response.LoginResponse;
import com.kishore.jws.project.security.JwtUtil;
import com.kishore.jws.project.service.AuthenticationService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

	private final JwtUtil jwtUtil;
	private final AuthenticationService authenticationService;
	public AuthenticationController(JwtUtil jwtUtil, AuthenticationService authenticationService) {
		super();
		this.jwtUtil = jwtUtil;
		this.authenticationService = authenticationService;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<User> register(@RequestBody SignInRequestDto signInRequestDto) {
		User registeredUser = authenticationService.signIn(signInRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequestDto loginRequestDto) {
		User authenticateUser = authenticationService.login(loginRequestDto);
		String jwtToken = jwtUtil.generateToken(authenticateUser); // ✅ Works now since User implements UserDetails
		LoginResponse loginResponse = new LoginResponse(jwtToken, jwtUtil.getExpirationTime());
		return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
	}

	
	@PostMapping("/verify")
	public ResponseEntity<?> verifyUser(@RequestBody VerifyUserRequestDto verifyUserRequestDto) {
		try {
			authenticationService.verifyUser(verifyUserRequestDto);
			return ResponseEntity.status(HttpStatus.OK).body("Account Verified Successfully");
		} catch(RuntimeException runtimeException) {
			return ResponseEntity.badRequest().body(runtimeException.getMessage());
		}
	}
	
	@PostMapping("/resend-verification")
	public ResponseEntity<?> resendVerification(@RequestParam(name = "email") String email) {
		try {
			authenticationService.resendVerficationCode(email);
			return ResponseEntity.status(HttpStatus.OK).body("Verification code sent");
		} catch(RuntimeException runtimeException) {
			return ResponseEntity.badRequest().body(runtimeException.getMessage());
		}
	}
}
