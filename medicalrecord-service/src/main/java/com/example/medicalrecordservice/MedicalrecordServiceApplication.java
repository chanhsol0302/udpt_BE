package com.example.medicalrecordservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MedicalrecordServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalrecordServiceApplication.class, args);
	}

}
