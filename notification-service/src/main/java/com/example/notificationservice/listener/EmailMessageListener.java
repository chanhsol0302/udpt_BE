package com.example.notificationservice.listener;

import com.example.notificationservice.service.NotificationService;
import com.example.notificationservice.config.RabbitMQConfig;
import com.example.common.dto.EmailMessage;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailMessageListener {

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void receiveEmailMessage(EmailMessage emailMessage) {
        try {
            System.out.println("Received email message from RabbitMQ: " + emailMessage.getTo());
            // Gọi service để gửi email
            notificationService.sendMail(
                emailMessage.getTo(),
                emailMessage.getSubject(),
                emailMessage.getBody()
            );
            System.out.println("Email sent successfully to " + emailMessage.getTo());
        } catch (Exception e) {
            System.err.println("Error processing email message for " + emailMessage.getTo() + ": " + e.getMessage());
        }
    }
}