package com.example.prescriptionservice.service;

import com.example.prescriptionservice.model.Prescription;
import com.example.prescriptionservice.dto.PrescriptionCreateRequest;

import com.example.prescriptionservice.repository.PrescriptionRepository;

import java.util.UUID;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionService {
	@Autowired
	private PrescriptionRepository prescriptionRepository;
	
	public Prescription createPrescription(PrescriptionCreateRequest request) {
		Prescription prescription = new Prescription();
		prescription.setId(UUID.randomUUID());
		prescription.setMedicines(request.getMedicines());
		prescription.setCreatedAt(LocalDateTime.now());
		
		prescriptionRepository.save(prescription);
		return prescription;
	}
	
	public Prescription getPrescriptionById(UUID id) {
		Optional<Prescription> prescription = prescriptionRepository.findById(id);
		if (prescription.isPresent()) {
			return prescription.get();
		} else {
			throw new RuntimeException("Prescription not found with ID: " + id);
		}
	}
}
