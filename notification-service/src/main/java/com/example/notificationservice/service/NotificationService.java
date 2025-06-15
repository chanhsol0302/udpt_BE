package com.example.notificationservice.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

@Service
public class NotificationService {
	@Autowired
	private JavaMailSender mailSender;
	
	// Gửi mail thanh toán
	public void SendMail(String toEmail, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom("chanhsol0302@gmail.com");
		message.setTo(toEmail);
		message.setSubject(subject);
		message.setText(body);
		
		try {
			mailSender.send(message);
			System.out.println("Email Send Successfully to " + toEmail);
		}
		catch (Exception e) {
			throw new RuntimeException("Failed to send email", e);
		}
	}
}