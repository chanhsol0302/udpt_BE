package com.example.illnessservice.service;

import com.example.illnessservice.model.Illness;
import com.example.illnessservice.repository.IllnessRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class IllnessService {
	@Autowired
	private IllnessRepository illnessRepository;
	
	public List<Illness> searchIllnessesByName(String query) {
		return illnessRepository.findByNameContainingIgnoreCase(query);
	}
	
	public Illness getIllnessById(UUID id) {
		return illnessRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(
						"Illness could not found with id: " + id));
	}
}