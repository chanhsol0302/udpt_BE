package com.example.prescriptionservice.service;

import com.example.prescriptionservice.model.Medicine;
import com.example.prescriptionservice.model.Prescription;
import com.example.prescriptionservice.client.DrugInventoryServiceClient;
import com.example.prescriptionservice.dto.PrescriptionCreateRequest;

import com.example.prescriptionservice.repository.PrescriptionRepository;

import java.util.UUID;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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
		
		// Tính medicine price
		float medicinePrice = 0;
		if (request.getMedicines() != null && !request.getMedicines().isEmpty()) {
			for (Medicine medicine : request.getMedicines()) {
				medicinePrice += (medicine.getPrice() * medicine.getQuantity());
			}
		}
		prescription.setMedicinePrice(medicinePrice);
		
		// set payments = false ở model rồi 
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
	
	public List<Prescription> getPrescriptionsByDate(LocalDate date) {
		LocalDateTime startOfDay = date.atStartOfDay();
		LocalDateTime endOfDay = LocalDateTime.of(date,LocalTime.MAX);
		
		List<Prescription> prescriptions = prescriptionRepository.findByCreatedAtBetween(startOfDay, endOfDay);
		if (prescriptions.isEmpty()) {
			throw new RuntimeException("No prescription is found for date: " + date);
		}
		return prescriptions;
	}
	public List<Prescription> getPrescriptionByDate(LocalDate date) {
		return getPrescriptionsByDate(date);
	}
	
	public Prescription updatePrescriptionPaymentsById(UUID id) {
		Optional<Prescription> prescription = prescriptionRepository.findById(id);
		if (prescription.isPresent()) {
			Prescription updatePrescription = prescription.get();
			updatePrescription.setPayments(true);
			return prescriptionRepository.save(updatePrescription);
		}
		else
		{
			throw new RuntimeException("No prescription is found for ID: " + id);
		}
	}
}
