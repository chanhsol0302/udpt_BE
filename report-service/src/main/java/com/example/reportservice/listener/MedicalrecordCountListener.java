package com.example.reportservice.listener;

import com.example.reportservice.config.RabbitMQConfig;
import com.example.reportservice.service.MedicalrecordCountService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicalrecordCountListener {
	@Autowired
	private MedicalrecordCountService countService;
	
    @RabbitListener(queues = RabbitMQConfig.PATIENT_QUEUE)
    public void receiveMedicalrecordMessage(String date) {
    	countService.updateMedicalrecordCount(date);
    }
}