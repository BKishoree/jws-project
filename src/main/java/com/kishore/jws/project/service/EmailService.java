package com.kishore.jws.project.service;

public interface EmailService {

	void sendVerificationEmail(String to, String subject, String description);
}
