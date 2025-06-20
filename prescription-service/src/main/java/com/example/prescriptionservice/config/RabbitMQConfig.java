package com.example.prescriptionservice.config;

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

    public static final String EMAIL_QUEUE = "email.queue";
    public static final String EMAIL_EXCHANGE = "email.exchange";
    public static final String EMAIL_ROUTING_KEY = "email.routingkey";
    
    public static final String PRESCRIPTION_QUEUE = "prescription.queue";
    public static final String PRESCRIPTION_EXCHANGE = "prescription.exchange";
    public static final String PRESCRIPTION_ROUTING_KEY = "prescription.routingkey";

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true); // true = durable
    }

    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange(EMAIL_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue emailQueue, TopicExchange emailExchange) {
        return org.springframework.amqp.core.BindingBuilder.bind(emailQueue)
                .to(emailExchange)
                .with(EMAIL_ROUTING_KEY);
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
    	return BindingBuilder.bind(prescriptionQueue)
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