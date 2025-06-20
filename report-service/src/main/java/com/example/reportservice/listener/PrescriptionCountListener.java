package com.example.reportservice.listener;

import com.example.reportservice.config.RabbitMQConfig;
import com.example.reportservice.service.PrescriptionCountService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionCountListener {
	@Autowired
	private PrescriptionCountService countService;
	
    @RabbitListener(queues = RabbitMQConfig.PRESCRIPTION_QUEUE)
    public void receivePrescriptionMessage(String date) {
    	countService.updatePrescriptionCount(date);
    }
}