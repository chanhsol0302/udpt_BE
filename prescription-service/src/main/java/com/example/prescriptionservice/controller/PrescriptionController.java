package com.example.prescriptionservice.controller;

import com.example.prescriptionservice.model.Prescription;
import com.example.prescriptionservice.service.PrescriptionService;
import com.example.prescriptionservice.dto.PrescriptionCreateRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;
import java.time.LocalDate;

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
    	Prescription prescription = prescriptionService.getPrescriptionById(id);
    	return new ResponseEntity<>(prescription, HttpStatus.OK);
    }
    
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Prescription>> getPrescriptionToday(@PathVariable LocalDate date) {
    	List<Prescription> prescriptions = prescriptionService.getPrescriptionByDate(date);
    	return new ResponseEntity<>(prescriptions, HttpStatus.OK);
    }
    
    @PutMapping("/updatePayments/{id}")
    public ResponseEntity<Prescription> updatePrescriptionPaymentsById(@PathVariable UUID id) {
    	Prescription prescription = prescriptionService.updatePrescriptionPaymentsById(id);
    	return new ResponseEntity<>(prescription, HttpStatus.OK);
    }
}