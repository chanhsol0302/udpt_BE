package com.example.medicalrecordservice.controller;

import com.example.medicalrecordservice.model.Medicalrecord;
import com.example.medicalrecordservice.service.MedicalrecordService;
import com.example.medicalrecordservice.dto.MedicalrecordCreateRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/medicalrecords")
public class MedicalrecordController {
	@Autowired
    private MedicalrecordService medicalrecordService;

    @PostMapping
    public ResponseEntity<Medicalrecord> createPrescription(@RequestBody MedicalrecordCreateRequest request) {
        Medicalrecord record = medicalrecordService.createMedicalrecord(request);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }
    
    @GetMapping("patient/{patientId}")
    public ResponseEntity<List<Medicalrecord>> getMedicalrecordByPatientId(@PathVariable UUID patientId) {
    	List<Medicalrecord> records = medicalrecordService.getMedicalrecordByPatientId(patientId);
    	return new ResponseEntity<>(records, HttpStatus.OK);
    }
    
    @GetMapping("doctor/{doctorId}")
    public ResponseEntity<List<Medicalrecord>> getMedicalrecordByDoctorId(@PathVariable UUID doctorId) {
    	List<Medicalrecord> records = medicalrecordService.getMedicalrecordByDoctorId(doctorId);
    	return new ResponseEntity<>(records, HttpStatus.OK);
    }
    
    @GetMapping("appointment/{appointmentId}")
    public ResponseEntity<List<Medicalrecord>> getMedicalrecordByAppointmentId(@PathVariable UUID appointmentId) {
    	List<Medicalrecord> records = medicalrecordService.getMedicalrecordByAppointmentId(appointmentId);
    	return new ResponseEntity<>(records, HttpStatus.OK);
    }
    
    @PutMapping("updatePayments/{id}")
    public ResponseEntity<Medicalrecord> updateMedicalrecordPaymentsById(@PathVariable UUID id) {
    	Medicalrecord record = medicalrecordService.updatePayments(id);
    	return new ResponseEntity<>(record, HttpStatus.OK);
    }
}