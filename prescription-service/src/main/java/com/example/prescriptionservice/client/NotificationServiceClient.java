package com.example.prescriptionservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notification-service", url = "http://localhost:8091")
public interface NotificationServiceClient {
	@PostMapping("/sendEmail") 
	void SendMail(@RequestParam String to, @RequestParam String subject, @RequestParam String body);
}