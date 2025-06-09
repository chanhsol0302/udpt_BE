package com.example.doctorservice.controller;

import com.example.doctorservice.model.Specialty;
import com.example.doctorservice.service.SpecialtyService;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController; 
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/specialties")
public class SpecialtyController {
	@Autowired
    private SpecialtyService specialtyService;

	@GetMapping
	public List<Specialty> getAllSpecialties() {
	    return specialtyService.getAllSpecialty();
	}
}