package com.example.doctorservice.controller;

import com.example.doctorservice.service.DoctorService;
import com.example.doctorservice.dto.DoctorDTO;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "http://localhost")
public class DoctorController {
	@Autowired
    private DoctorService doctorService;

	@GetMapping("specialty/{id}")
	public List<DoctorDTO> getDoctorsBySpecialtyId(@PathVariable UUID id) {
	    return doctorService.getDoctorsBySpecialtyId(id);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<DoctorDTO> getDoctorByUserId(@PathVariable UUID userId) {
		Optional<DoctorDTO> doctorDTO = doctorService.getDoctorIdByUserId(userId);
		return doctorDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}