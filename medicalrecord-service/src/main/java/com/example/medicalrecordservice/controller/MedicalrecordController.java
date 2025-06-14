package com.example.medicalrecordservice.controller;

import com.example.medicalrecordservice.model.Medicalrecord;
import com.example.medicalrecordservice.service.MedicalrecordService;
import com.example.medicalrecordservice.dto.MedicalrecordCreateRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;

//import java.util.UUID;

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
}