package com.example.prescriptionservice.controller;

import com.example.prescriptionservice.model.Prescription;
import com.example.prescriptionservice.service.PrescriptionService;
import com.example.prescriptionservice.dto.PrescriptionCreateRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {
	@Autowired
    private PrescriptionService prescriptionService;

    @PostMapping
    public ResponseEntity<Prescription> createPrescription(@RequestBody PrescriptionCreateRequest request) {
        Prescription prescription = prescriptionService.createPrescription(request);
        return new ResponseEntity<>(prescription, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable UUID id) {
    	Prescription prescription = null;
    	try {
    		prescription = prescriptionService.getPrescriptionById(id);
    	}
    	catch (RuntimeException e) {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    	}
    	return new ResponseEntity<>(prescription, HttpStatus.OK);
    }
}