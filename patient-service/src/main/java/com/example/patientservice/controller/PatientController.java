package com.example.patientservice.controller;

import com.example.patientservice.dto.PatientCreateRequest;
import com.example.patientservice.dto.PatientResponse;
import com.example.patientservice.dto.PatientUpdateRequest;
import com.example.patientservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest; // Import HttpServletRequest

import jakarta.validation.Valid; // Để sử dụng validation
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@Valid @RequestBody PatientCreateRequest request) {
        PatientResponse newPatient = patientService.createPatient(request);
        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable UUID id) {
        return patientService.getPatientById(id)
                .map(patient -> new ResponseEntity<>(patient, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PatientResponse> getPatientByUserId(@PathVariable UUID userId, HttpServletRequest request) {
        // Lấy userId
    	UUID userIdFromToken = (UUID) request.getAttribute("userIdFromToken");
    	
    	if (!userIdFromToken.equals(userId)) {
            // Nếu userId trong token không khớp với userId trong URL (mà bệnh nhân muốn xem)
            // -> Đây là nỗ lực truy cập trái phép vào hồ sơ của người khác.
            return ResponseEntity.status(403).body(null); // Trả về 403 Forbidden
        }
    	
    	return patientService.getPatientByUserId(userId)
                .map(patient -> new ResponseEntity<>(patient, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable UUID id, @Valid @RequestBody PatientUpdateRequest request) {
        try {
            PatientResponse updatedPatient = patientService.updatePatient(id, request);
            return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        try {
            patientService.deletePatient(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}