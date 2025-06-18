package com.example.prescriptionservice.service;

import com.example.prescriptionservice.model.Medicine;
import com.example.prescriptionservice.model.Prescription;
import com.example.prescriptionservice.dto.PatientResponse;
import com.example.prescriptionservice.dto.UserResponse;
import com.example.common.dto.EmailMessage;
import com.example.prescriptionservice.client.DrugInventoryServiceClient;
import com.example.prescriptionservice.client.PatientServiceClient;
import com.example.prescriptionservice.client.UserServiceClient;
import com.example.prescriptionservice.dto.PrescriptionCreateRequest;
import com.example.prescriptionservice.config.RabbitMQConfig;

import com.example.prescriptionservice.repository.PrescriptionRepository;

import java.util.UUID;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Service
public class PrescriptionService {
	@Autowired
	private PrescriptionRepository prescriptionRepository;
	
	@Autowired
	private DrugInventoryServiceClient drugClient;
	
	@Autowired
	private PatientServiceClient patientServiceClient;
	
	@Autowired
	private UserServiceClient userServiceClient;
	
	//@Autowired
	//private NotificationServiceClient notificationServiceClient;
	
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	public Prescription createPrescription(PrescriptionCreateRequest request) {
		Prescription prescription = new Prescription();
		prescription.setId(UUID.randomUUID());
		prescription.setPatientId(request.getPatientId());
		prescription.setMedicalrecordId(request.getMedicalrecordId());		
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
			throw new RuntimeException("No prescription is found with ID: " + id + " for payments update");
		}
	}
	
	public Prescription preparingPrescription(UUID id, UUID pharmasticId) {
		Optional<Prescription> prescription = prescriptionRepository.findById(id);
		if (prescription.isPresent()) {
			Prescription preparingPres = prescription.get();
			preparingPres.setPharmacistId(pharmasticId);
			preparingPres.setStatus("PREPARING");
			return prescriptionRepository.save(preparingPres);
		}
		else
		{
			throw new RuntimeException("No prescription is found with ID:" + id + " for status update");
		}
	}
	
	public Prescription updateStatus(UUID id, String status) {
		Optional<Prescription> prescription = prescriptionRepository.findById(id);
		if (prescription.isPresent()) {
			Prescription preparingPres = prescription.get();
			preparingPres.setStatus(status);
			// Nếu status = "PREPARED" thì gửi mail
			if ("PREPARED".equals(status)) {
				PatientResponse patient = patientServiceClient.getPatientById(preparingPres.getPatientId());
		        UserResponse user = userServiceClient.getUserById(patient.getUserId());
		        
		        EmailMessage emailMessage = new EmailMessage(
		                user.getEmail(),
		                "THÔNG BÁO BỆNH VIỆN",
		                "Bạn đã có thể đến quầy để lấy thuốc."
		            );
	
		            try {
		                // Gửi tin nhắn đến RabbitMQ
		                rabbitTemplate.convertAndSend(
		                    RabbitMQConfig.EMAIL_EXCHANGE,
		                    RabbitMQConfig.EMAIL_ROUTING_KEY,
		                    emailMessage
		                );
		                System.out.println("Email message sent to RabbitMQ for: " + user.getEmail());
		            } catch (Exception e) {
		                System.err.println("Failed to send email message to RabbitMQ: " + e.getMessage());
		            }
			}
	        //notificationServiceClient.SendMail(user.getEmail(), "THÔNG BÁO BỆNH VIỆN", "Bạn đã có thể đến quầy để lấy thuốc.");
	        
			return prescriptionRepository.save(preparingPres);
		}
		else
		{
			throw new RuntimeException("No prescription is found with ID:" + id + " for status update");
		}
	}
	
	
}
