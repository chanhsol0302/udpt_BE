package com.example.medicalrecordservice.service;

import com.example.medicalrecordservice.model.Medicalrecord;
import com.example.medicalrecordservice.dto.PrescriptionCreateRequest;
import com.example.medicalrecordservice.dto.MedicalrecordCreateRequest;
import com.example.medicalrecordservice.dto.PrescriptionCreateResponse;
import com.example.medicalrecordservice.dto.TreatmentResponse;

import com.example.medicalrecordservice.repository.MedicalrecordRepository;
import com.example.medicalrecordservice.client.PrescriptionServiceClient;
import com.example.medicalrecordservice.client.TreatmentServiceClient;

import java.util.UUID;
import java.time.LocalDateTime;
//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MedicalrecordService {
	@Autowired
	private MedicalrecordRepository medicalrecordRepository;
	
	@Autowired
	private PrescriptionServiceClient prescriptionServiceClient;
	
	@Autowired
	private TreatmentServiceClient treatmentServiceClient;
	
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
		
		// Tính giá treatment
		float treatmentTotalPrice = 0;
		if (request.getTreatmentId() != null && !request.getTreatmentId().isEmpty()) {
			for (UUID treatmentId: request.getTreatmentId()) {
				try {
					ResponseEntity<TreatmentResponse> treatment = treatmentServiceClient.getTreatmentById(treatmentId);
					
					if (treatment.getStatusCode().is2xxSuccessful() && treatment.getBody() != null) {
						treatmentTotalPrice = treatmentTotalPrice + (treatment.getBody().getPrice());
					} else {
						throw new RuntimeException("Failed to get treatment details for ID: " + treatmentId);
					}
				}
				catch (RuntimeException ex) {
					throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
				}
			}
		}
		medicalrecord.setTotalPrice(treatmentTotalPrice);
		
		//medicalrecord.setTotalPrice(request.getTotalPrice());
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
