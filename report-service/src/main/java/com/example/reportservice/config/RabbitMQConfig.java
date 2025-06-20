package com.example.reportservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class RabbitMQConfig {
    // Report part
    public static final String PATIENT_QUEUE = "patient.queue";
    public static final String PATIENT_EXCHANGE = "patient.exchange";
    public static final String PATIENT_ROUTING_KEY = "patient.routingkey";
    
    public static final String PRESCRIPTION_QUEUE = "prescription.queue";
    public static final String PRESCRIPTION_EXCHANGE = "prescription.exchange";
    public static final String PRESCRIPTION_ROUTING_KEY = "prescription.routingkey";
    
    // Bean queue mới cho tin nhắn "patient"
    @Bean
    public Queue patientQueue() {
    	return new Queue(PATIENT_QUEUE, true);
    }
    
    // Bean topicExchange mới cho tin nhắn "patient"
    @Bean
    public TopicExchange patientExchange() {
    	return new TopicExchange(PATIENT_EXCHANGE);
    }
    
    // Bean Binding mới cho tin nhắn "patient"
    @Bean
    public Binding patientBinding(Queue patientQueue, TopicExchange patientExchange) {
    	return BindingBuilder.bind(patientQueue)
    			.to(patientExchange)
    			.with(PATIENT_ROUTING_KEY);
    }
    
    // Prescription Report
    @Bean
    public Queue prescriptionQueue() {
    	return new Queue(PRESCRIPTION_QUEUE, true);
    }
    
    @Bean
    public TopicExchange prescriptionExchange() {
    	return new TopicExchange(PRESCRIPTION_EXCHANGE);
    }
    
    @Bean
    public Binding prescriptionBinding(Queue prescriptionQueue, TopicExchange prescriptionExchange) {
    	return BindingBuilder.bind(prescriptionExchange)
    			.to(prescriptionExchange)
    			.with(PRESCRIPTION_ROUTING_KEY);
    }
    
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter); // Set converter cho RabbitTemplate
        return rabbitTemplate;
    }
}