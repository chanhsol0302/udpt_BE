package com.example.treatmentservice.service;

import com.example.treatmentservice.model.Treatment;
import com.example.treatmentservice.repository.TreatmentRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class TreatmentService {
	@Autowired
	private TreatmentRepository treatmentRepository;
	
	public List<Treatment> searchTreatmentsByName(String query) {
		return treatmentRepository.findByNameContainingIgnoreCase(query);
	}
}