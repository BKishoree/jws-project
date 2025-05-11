package com.kishore.jws.project.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.kishore.jws.project.service.EmailService;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendVerificationEmail(String to, String subject, String description) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			helper.setTo(to);
			helper.setSubject(subject);
//			helper.setText(description);
			helper.setText(description, true);
			
			javaMailSender.send(message);
		} catch(Exception exception) {
			System.out.println("An error occurred: "+ exception.getMessage());
		}
		
	}
}
