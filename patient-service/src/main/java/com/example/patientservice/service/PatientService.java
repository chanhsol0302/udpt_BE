package com.example.patientservice.service;

import com.example.patientservice.dto.PatientCreateRequest;
import com.example.patientservice.dto.PatientResponse;
import com.example.patientservice.dto.PatientUpdateRequest;
import com.example.patientservice.model.Patient;
import com.example.patientservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Transactional
    public PatientResponse createPatient(PatientCreateRequest request) {
        if (patientRepository.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("Patient record already exists for this user ID.");
        }

        Patient patient = new Patient();
        patient.setUserId(request.getUserId());
        patient.setName(request.getName());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setAddress(request.getAddress());
        patient.setPhone(request.getPhone());

       
        Patient savedPatient = patientRepository.save(patient);
        return convertToPatientResponse(savedPatient);
    }

    public Optional<PatientResponse> getPatientById(UUID id) {
        return patientRepository.findById(id).map(this::convertToPatientResponse);
    }

    public Optional<PatientResponse> getPatientByUserId(UUID userId) {
        return patientRepository.findByUserId(userId).map(this::convertToPatientResponse);
    }

    @Transactional
    public PatientResponse updatePatient(UUID id, PatientUpdateRequest request) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + id));

        // Cập nhật các trường nếu chúng không null
        if (request.getName() != null && !request.getName().isEmpty()) {
            existingPatient.setName(request.getName());
        }
        if (request.getDateOfBirth() != null) {
            existingPatient.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getGender() != null && !request.getGender().isEmpty()) {
            existingPatient.setGender(request.getGender());
        }
        if (request.getAddress() != null && !request.getAddress().isEmpty()) {
            existingPatient.setAddress(request.getAddress());
        }
        if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            existingPatient.setPhone(request.getPhone());
        }

        Patient updatedPatient = patientRepository.save(existingPatient);
        return convertToPatientResponse(updatedPatient);
    }

    @Transactional
    public void deletePatient(UUID id) {
        if (!patientRepository.existsById(id)) {
            throw new IllegalArgumentException("Patient not found with ID: " + id);
        }
        patientRepository.deleteById(id);
    }

    // Helper method để chuyển đổi Patient entity sang DTO
    private PatientResponse convertToPatientResponse(Patient patient) {
        PatientResponse response = new PatientResponse();
        response.setId(patient.getId());
        response.setUserId(patient.getUserId());
        response.setName(patient.getName());
        response.setDateOfBirth(patient.getDateOfBirth());
        response.setGender(patient.getGender());
        response.setAddress(patient.getAddress());
        response.setPhone(patient.getPhone());
        response.setCreatedAt(patient.getCreatedAt());
        response.setUpdatedAt(patient.getUpdatedAt());
        return response;
    }
}