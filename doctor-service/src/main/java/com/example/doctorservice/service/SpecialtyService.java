package com.example.doctorservice.service;

import com.example.doctorservice.model.Specialty;
import com.example.doctorservice.repository.SpecialtyRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SpecialtyService {
	@Autowired
	private SpecialtyRepository specialtyRepository;
	
	public List<Specialty> getAllSpecialty() {
		return specialtyRepository.findAll();
	}
}