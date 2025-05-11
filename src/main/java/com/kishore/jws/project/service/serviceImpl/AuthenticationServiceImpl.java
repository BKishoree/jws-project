package com.kishore.jws.project.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kishore.jws.project.constants.MailTemplate;
import com.kishore.jws.project.entity.User;
import com.kishore.jws.project.model.request.LoginRequestDto;
import com.kishore.jws.project.model.request.SignInRequestDto;
import com.kishore.jws.project.model.request.VerifyUserRequestDto;
import com.kishore.jws.project.repository.UserRepository;
import com.kishore.jws.project.service.AuthenticationService;
import com.kishore.jws.project.service.EmailService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final EmailService emailService;
	
	public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, EmailService emailService) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.emailService = emailService;
	}

	@Override
	public User signIn(SignInRequestDto signInRequestDto) {
		try {
			User user = new User(signInRequestDto.getUserName(), signInRequestDto.getEmail(), passwordEncoder.encode(signInRequestDto.getPassword()));
			user.setVerificationCode(generateVerificationCode());
			user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
			user.setUserActive(false);
			sendVerificationEmail(user);
			return userRepository.save(user);
		} catch(Exception exception) {
			return null;
		}
	}

	@Override
	public User login(LoginRequestDto loginRequestDto) {
		User user = userRepository.findByEmail(loginRequestDto.getEmail())
			.orElseThrow(() -> new RuntimeException("User not found"));

		if (!user.isEnabled()) {
			throw new RuntimeException("Account not verified. Please verify your account");
		}

		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
		);

		return user;
	}

	
	@Override
	public void verifyUser(VerifyUserRequestDto verifyUserRequestDto) {
		try {
			Optional<User> optionalUser = userRepository.findByEmail(verifyUserRequestDto.getEmail());
			if(optionalUser.isPresent()) {
				User user = optionalUser.get();
				
				if(user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
					throw new RuntimeException("Verification code has expired");
				}
				if(user.getVerificationCode().equals(verifyUserRequestDto.getVerificationCode())) {
					user.setUserActive(true);
					user.setVerificationCode(null);
					user.setVerificationCodeExpiresAt(null);
					userRepository.save(user);
				} else {
					throw new RuntimeException("Invalid verification code");
				}
			} else {
				throw new RuntimeException("User not found");
			}
		} catch(Exception exception) {
			System.out.println("An error occured: "+ exception.getMessage());
		}
	}
	
	@Override
	public void resendVerficationCode(String email) {
		try {
			Optional<User> optionalUser = userRepository.findByEmail(email);
			if(optionalUser.isPresent()) {
				User user = optionalUser.get();
				if(user.isEnabled()) {
					throw new RuntimeException("Account is already verified");
				}
				
				user.setVerificationCode(generateVerificationCode());
				user.setVerificationCodeExpiresAt(LocalDateTime.now().plusHours(1));
				sendVerificationEmail(user);
				userRepository.save(user);
			} else {
				throw new RuntimeException("User not found");
			}
		} catch(Exception exception) {
			System.out.println("An error occured: "+ exception.getMessage());
		}
	}
	
	public void sendVerificationEmail(User user) {
		String subject = "Account Verification";
		String verificationCode = user.getVerificationCode();
		String htmlMessage = MailTemplate.DESCRIPTION.replace("${verificationCode}", verificationCode);
		
		try {
			emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
		} catch(Exception exception) {
			exception.getMessage();
		}
	}
	
	private String generateVerificationCode() {
		Random random = new Random();
		int code = random.nextInt(900000) + 100000;
		return String.valueOf(code);
	}
	
}
