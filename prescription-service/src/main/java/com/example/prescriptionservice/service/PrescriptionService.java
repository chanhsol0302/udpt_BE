package com.example.prescriptionservice.service;

import com.example.prescriptionservice.model.Prescription;
import com.example.prescriptionservice.dto.PrescriptionCreateRequest;

import com.example.prescriptionservice.repository.PrescriptionRepository;

import java.util.UUID;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionService {
	@Autowired
	private PrescriptionRepository prescriptionRepository;
	
	public Prescription createPrescription(PrescriptionCreateRequest request) {
		Prescription prescription = new Prescription();
		//prescription.setId(UUID.randomUUID());
		prescription.setMedicines(request.getMedicines());
		//prescription.setCreatedAt(LocalDateTime.now());
		
		prescriptionRepository.save(prescription);
		return prescription;
	}
}
