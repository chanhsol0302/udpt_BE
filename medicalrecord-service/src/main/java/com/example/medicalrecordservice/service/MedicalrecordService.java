package com.example.medicalrecordservice.service;

import com.example.medicalrecordservice.model.Medicalrecord;
import com.example.medicalrecordservice.dto.PrescriptionCreateRequest;
import com.example.medicalrecordservice.dto.MedicalrecordCreateRequest;
import com.example.medicalrecordservice.dto.PrescriptionCreateResponse;
import com.example.medicalrecordservice.dto.TreatmentResponse;

import com.example.medicalrecordservice.repository.MedicalrecordRepository;
import com.example.medicalrecordservice.client.AppointmentServiceClient;
import com.example.medicalrecordservice.client.PrescriptionServiceClient;
import com.example.medicalrecordservice.client.TreatmentServiceClient;

import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;

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
	
	@Autowired
	private AppointmentServiceClient appointmentServiceClient;
	
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
		medicalrecord.setTreatmentPrice(treatmentTotalPrice);
		
		//medicalrecord.setTotalPrice(request.getTotalPrice());
		medicalrecord.setNote(request.getNote());
		medicalrecord.setCreatedAt(LocalDateTime.now());
		
		// Chuẩn bị dữ liệu cho Prescription Service
        PrescriptionCreateRequest prescriptionRequest = new PrescriptionCreateRequest();
        prescriptionRequest.setMedicines(request.getMedicines());
		
        ResponseEntity<PrescriptionCreateResponse> prescriptionResponse = prescriptionServiceClient.createPrescription(prescriptionRequest);
        
        // Lấy dữ liệu trả về từ prescription service
        UUID prescriptionId = prescriptionResponse.getBody().getId();
        medicalrecord.setPrescriptionId(prescriptionId);
        
        float medicinePrice = prescriptionResponse.getBody().getMedicinePrice();
        medicalrecord.setMedicinePrice(medicinePrice);
		
        medicalrecord.setTotalPrice(treatmentTotalPrice + medicinePrice);
        // Đã set payments = false ở model 
        
        medicalrecordRepository.save(medicalrecord);
        
        // Cập nhật khám thành công (Appointment.status = 2)
        appointmentServiceClient.updateStatus(request.getAppointmentId(), 2);
		return medicalrecord;
	}
	
	public List<Medicalrecord> getMedicalrecordByPatientId(UUID patientId) {
        List<Medicalrecord> records = medicalrecordRepository.findByPatientId(patientId);
        if (records.isEmpty()) {
        	throw new RuntimeException("No medical records is found for patient id: " + patientId);
        }
        return records;
	}

	public List<Medicalrecord> getMedicalrecordByDoctorId(UUID doctorId) {
		List<Medicalrecord> records = medicalrecordRepository.findByDoctorId(doctorId);
		if (records.isEmpty()) {
			throw new RuntimeException("No medical record is found for doctor ID: " + doctorId);
		}
		return records;
	}
	
	public List<Medicalrecord> getMedicalrecordByAppointmentId(UUID appointmentId) {
		List<Medicalrecord> records = medicalrecordRepository.findByAppointmentId(appointmentId);
		if (records.isEmpty()) {
			throw new RuntimeException("No medical record is found for appointment ID: " + appointmentId);
		}
		return records;
	}
}
