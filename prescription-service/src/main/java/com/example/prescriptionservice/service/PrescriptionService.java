package com.example.prescriptionservice.service;

import com.example.prescriptionservice.model.Medicine;
import com.example.prescriptionservice.model.Prescription;
import com.example.prescriptionservice.client.DrugInventoryServiceClient;
import com.example.prescriptionservice.dto.PrescriptionCreateRequest;

import com.example.prescriptionservice.repository.PrescriptionRepository;

import java.util.UUID;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionService {
	@Autowired
	private PrescriptionRepository prescriptionRepository;
	
	@Autowired
	private DrugInventoryServiceClient drugClient;
	
	public Prescription createPrescription(PrescriptionCreateRequest request) {
		Prescription prescription = new Prescription();
		prescription.setId(UUID.randomUUID());
		prescription.setMedicines(request.getMedicines());
		prescription.setCreatedAt(LocalDateTime.now());
		
		prescriptionRepository.save(prescription);
		
		//Update số lượng ở kho
		if (request.getMedicines() != null && !request.getMedicines().isEmpty()) {
			for (Medicine medicine : request.getMedicines()) {
				try {
					drugClient.updateAvailableQuantity(medicine.getMedicineId(), medicine.getQuantity());
				} catch (Exception e) {
					throw new RuntimeException("Cannot update quantity for " + medicine.getMedicineId(), e);
				}
			}
		}
		return prescription;
	}
	
	public Prescription getPrescriptionById(UUID id) {
		return prescriptionRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(
						"Prescription could not found with id: " + id));
	}
}
