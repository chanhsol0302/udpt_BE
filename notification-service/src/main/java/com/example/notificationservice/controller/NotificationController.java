package com.example.notificationservice.controller;

import com.example.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
	@Autowired
	private NotificationService notificationService;
	
	@PostMapping("/sendEmail")
	public ResponseEntity<String> sendTestEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body) {
        try {
            notificationService.sendMail(to, subject, body);
            return new ResponseEntity<>("Email sent successfully to " + to, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error sending email: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}