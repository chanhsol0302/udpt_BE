package com.example.doctorservice.controller;

import com.example.doctorservice.service.PharmacistService;
import com.example.doctorservice.model.Pharmacist;

import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.UUID;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/pharmacists")
@CrossOrigin(origins = "http://localhost")
public class PharmacistController {
	@Autowired
    private PharmacistService pharmacistService;

	@GetMapping("/{userId}")
	public ResponseEntity<Pharmacist> getPharmacistByUserId(@PathVariable UUID userId) {
		Pharmacist pharmacist = pharmacistService.getPharmacistByUserId(userId);
		return new ResponseEntity<>(pharmacist, HttpStatus.OK);
	}
}