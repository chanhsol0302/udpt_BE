package com.example.medicalrecordservice.service;

import com.example.medicalrecordservice.model.Medicalrecord;
import com.example.medicalrecordservice.dto.PrescriptionCreateRequest;
import com.example.medicalrecordservice.dto.MedicalrecordCreateRequest;
import com.example.medicalrecordservice.dto.PrescriptionCreateResponse;

import com.example.medicalrecordservice.repository.MedicalrecordRepository;
import com.example.medicalrecordservice.client.PrescriptionServiceClient;

import java.util.UUID;
import java.time.LocalDateTime;
//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

@Service
public class MedicalrecordService {
	@Autowired
	private MedicalrecordRepository medicalrecordRepository;
	
	@Autowired
	private PrescriptionServiceClient prescriptionServiceClient;
	
	public Medicalrecord createMedicalrecord(MedicalrecordCreateRequest request) {
		Medicalrecord medicalrecord = new Medicalrecord();
		medicalrecord.setId(UUID.randomUUID());
		medicalrecord.setPatientId(request.getPatientId());
		medicalrecord.setPatientName(request.getPatientName());
		medicalrecord.setDoctorId(request.getDoctorId());
		medicalrecord.setDoctorName(request.getDoctorName());
		medicalrecord.setAppointmentId(request.getAppointmentId());
		medicalrecord.setVisitDate(request.getVisitDate());
		medicalrecord.setIllnessId(request.getIllnessId());
		medicalrecord.setTreatmentId(request.getTreatmentId());
		medicalrecord.setTotalPrice(request.getTotalPrice());
		medicalrecord.setNote(request.getNote());
		medicalrecord.setCreatedAt(LocalDateTime.now());
		
		// Chuẩn bị dữ liệu cho Prescription Service
        PrescriptionCreateRequest prescriptionRequest = new PrescriptionCreateRequest();
        prescriptionRequest.setMedicines(request.getMedicines());
		
        ResponseEntity<PrescriptionCreateResponse> prescriptionResponse = prescriptionServiceClient.createPrescription(prescriptionRequest);
        
        UUID prescriptionId = prescriptionResponse.getBody().getId();
        medicalrecord.setPrescriptionId(prescriptionId);
		
        medicalrecordRepository.save(medicalrecord);
		return medicalrecord;
	}
}
