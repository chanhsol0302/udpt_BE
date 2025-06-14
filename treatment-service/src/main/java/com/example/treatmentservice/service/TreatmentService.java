package com.example.treatmentservice.service;

import com.example.treatmentservice.model.Treatment;
import com.example.treatmentservice.repository.TreatmentRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TreatmentService {
	@Autowired
	private TreatmentRepository treatmentRepository;
	
	public List<Treatment> searchTreatmentsByName(String query) {
		return treatmentRepository.findByNameContainingIgnoreCase(query);
	}
	
	public Treatment getTreatmentById(UUID id) {
		Optional<Treatment> treatment = treatmentRepository.findById(id);
		if (treatment.isPresent()) {
			return treatment.get();
		} else {
			throw new RuntimeException("Treatment not found with ID: " + id);
		}
	}
}