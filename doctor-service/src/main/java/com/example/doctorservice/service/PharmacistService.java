package com.example.doctorservice.service;

import com.example.doctorservice.model.Pharmacist;
import com.example.doctorservice.repository.PharmacistRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Service
public class PharmacistService {
	@Autowired
	private PharmacistRepository pharmacistRepository;
	
	public Pharmacist getPharmacistByUserId(UUID userId) {
		return pharmacistRepository.findByUserId(userId)
				.orElseThrow(() -> new RuntimeException("No pharmacist is found with userID: " + userId));
	}
}