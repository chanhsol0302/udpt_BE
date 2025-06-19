package com.example.medicalrecordservice.service;

import com.example.medicalrecordservice.model.Medicalrecord;
import com.example.medicalrecordservice.model.Medicine;
import com.example.medicalrecordservice.dto.PrescriptionCreateRequest;
import com.example.medicalrecordservice.dto.IllnessResponse;
import com.example.medicalrecordservice.dto.MedicalrecordCreateRequest;
import com.example.medicalrecordservice.dto.PatientResponse;
import com.example.medicalrecordservice.dto.PrescriptionCreateResponse;
import com.example.medicalrecordservice.dto.TreatmentResponse;
import com.example.medicalrecordservice.dto.UserResponse;
import com.example.common.dto.EmailMessage;
import com.example.medicalrecordservice.config.RabbitMQConfig;
import com.example.medicalrecordservice.repository.MedicalrecordRepository;
import com.example.medicalrecordservice.client.AppointmentServiceClient;
import com.example.medicalrecordservice.client.IllnessServiceClient;
//import com.example.medicalrecordservice.client.NotificationServiceClient;
import com.example.medicalrecordservice.client.PatientServiceClient;
import com.example.medicalrecordservice.client.PrescriptionServiceClient;
import com.example.medicalrecordservice.client.TreatmentServiceClient;
import com.example.medicalrecordservice.client.UserServiceClient;

import java.util.UUID;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

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
	
	//@Autowired
	//private NotificationServiceClient notificationServiceClient;
	
	@Autowired
	private PatientServiceClient patientServiceClient;
	
	@Autowired
	private UserServiceClient userServiceClient;
	
	@Autowired
	private IllnessServiceClient illnessServiceClient;
	
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
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
		List<String> treatmentNames = new ArrayList<>();
		float treatmentTotalPrice = 0;
		if (request.getTreatmentId() != null && !request.getTreatmentId().isEmpty()) {
			for (UUID treatmentId: request.getTreatmentId()) {
				try {
					ResponseEntity<TreatmentResponse> treatment = treatmentServiceClient.getTreatmentById(treatmentId);
					
					if (treatment.getStatusCode().is2xxSuccessful() && treatment.getBody() != null) {
						treatmentTotalPrice = treatmentTotalPrice + (treatment.getBody().getPrice());
						treatmentNames.add(treatment.getBody().getName());
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
        prescriptionRequest.setPatientId(request.getPatientId());
        
        ResponseEntity<PrescriptionCreateResponse> prescriptionResponse = prescriptionServiceClient.createPrescription(prescriptionRequest);
        
        // Lấy dữ liệu trả về từ prescription service
        UUID prescriptionId = prescriptionResponse.getBody().getId();
        medicalrecord.setPrescriptionId(prescriptionId);
        
        float medicinePrice = prescriptionResponse.getBody().getMedicinePrice();
        medicalrecord.setMedicinePrice(medicinePrice);
		
        medicalrecord.setTotalPrice(treatmentTotalPrice + medicinePrice);
        // Đã set payments = false ở model 
        
        // Lưu xuống DB
        medicalrecordRepository.save(medicalrecord);
        
        // Gửi tin nhắn đến service Report
        sendPatientMessage(medicalrecord.getVisitDate().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        
        // Cập nhật khám thành công (Appointment.status = 2)
        appointmentServiceClient.updateStatus(request.getAppointmentId(), 2);
        
        // Gửi mail cho người dùng
        PatientResponse patient = patientServiceClient.getPatientById(request.getPatientId());
        UserResponse user = userServiceClient.getUserById(patient.getUserId());
        
        // Bắt đầu xây dựng chuỗi emailBody
        StringBuilder emailBodyBuilder = new StringBuilder();

        emailBodyBuilder.append("Kính gửi ").append(medicalrecord.getPatientName()).append(",\n\n");
        emailBodyBuilder.append("Chúng tôi gửi chi tiết hồ sơ bệnh án của bạn:\n\n");

        // Thông tin chung về bệnh án
        emailBodyBuilder.append("Mã hồ sơ: ").append(medicalrecord.getId()).append("\n");
        emailBodyBuilder.append("Tên bệnh nhân: ").append(medicalrecord.getPatientName()).append("\n");
        emailBodyBuilder.append("Tên bác sĩ: ").append(medicalrecord.getDoctorName()).append("\n");
        emailBodyBuilder.append("Ngày khám: ").append(medicalrecord.getVisitDate().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))).append("\n");
        emailBodyBuilder.append("Ghi chú: ").append(medicalrecord.getNote() != null && !medicalrecord.getNote().isEmpty() ? medicalrecord.getNote() : "Không có").append("\n\n");

        // Illnesses
        List<String> illnessNames = new ArrayList<>();
		if (request.getIllnessId() != null && !request.getIllnessId().isEmpty()) {
			for (UUID illnessId: request.getIllnessId()) {
				try {
					ResponseEntity<IllnessResponse> illness = illnessServiceClient.getIllnessById(illnessId);
					
					if (illness.getStatusCode().is2xxSuccessful() && illness.getBody() != null) {
						illnessNames.add(illness.getBody().getName());
					} else {
						throw new RuntimeException("Failed to get illness details for ID: " + illnessId);
					}
				}
				catch (RuntimeException ex) {
					throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
				}
			}
		}
		
		if (illnessNames != null && !illnessNames.isEmpty()) {
            emailBodyBuilder.append("Các bệnh đã mắc:\n");
            for (String illnessName : illnessNames) {
                emailBodyBuilder.append("  - ").append(illnessName).append("\n");
            }
            emailBodyBuilder.append("\n");
        } else {
            emailBodyBuilder.append("Không có bệnh nào được ghi nhận.\n\n");
        }
		
        // Treatments
        if (treatmentNames != null && !treatmentNames.isEmpty()) {
            emailBodyBuilder.append("Các phương pháp điều trị:\n");
            for (String treatmentName : treatmentNames) {
                emailBodyBuilder.append("  - ").append(treatmentName).append("\n");
            }
            emailBodyBuilder.append("\n");
        } else {
            emailBodyBuilder.append("Không có phương pháp điều trị nào.\n\n");
        }
        
        // Thông tin về thuốc
        List<Medicine> medicines = request.getMedicines();
        if (medicines != null && !medicines.isEmpty()) {
            emailBodyBuilder.append("Đơn thuốc:\n");
            for (Medicine med : medicines) {
                emailBodyBuilder.append("  - Tên thuốc: ").append(med.getName())
                              .append(", Số lượng: ").append(med.getQuantity())
                              .append(", Giá: ").append(String.format("%.0f", med.getPrice())).append(" VND")
                              .append(", Ghi chú: ").append(med.getNote() != null && !med.getNote().isEmpty() ? med.getNote() : "Không có")
                              .append("\n");
            }
            emailBodyBuilder.append("\n");
        } else {
            emailBodyBuilder.append("Không có đơn thuốc.\n\n");
        }

        // Thông tin về giá cả
        emailBodyBuilder.append("Thông tin thanh toán:\n");
        emailBodyBuilder.append("Giá thuốc: ").append(String.format("%.0f", medicalrecord.getMedicinePrice())).append(" VND\n");
        emailBodyBuilder.append("Giá điều trị: ").append(String.format("%.0f", medicalrecord.getTreatmentPrice())).append(" VND\n");
        emailBodyBuilder.append("Tổng cộng: ").append(String.format("%.0f", medicalrecord.getTotalPrice())).append(" VND\n");
        emailBodyBuilder.append("Trạng thái thanh toán: ").append(medicalrecord.isPayments() ? "Đã thanh toán" : "Chưa thanh toán").append("\n\n");

        emailBodyBuilder.append("Trân trọng,\n");
        emailBodyBuilder.append("Bệnh viện");

        String emailBody = emailBodyBuilder.toString();

        // Sau đó bạn gọi dịch vụ gửi mail
        //notificationServiceClient.SendMail(user.getEmail(), "Chi tiết Hồ sơ Bệnh án của bạn", emailBody);
		// Thay thế bằng RabbitMQ
        EmailMessage emailMessage = new EmailMessage(
                user.getEmail(),
                "Chi tiết Hồ sơ Bệnh án của bạn",
                emailBody
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
        return medicalrecord;
	}
	
	public void sendPatientMessage(String date) {
		try {
			rabbitTemplate.convertAndSend(
					RabbitMQConfig.PATIENT_EXCHANGE,
					RabbitMQConfig.PATIENT_ROUTING_KEY,
					date
			);
			System.out.println("Date message to RabbitMQ: " + date);
		} catch (Exception e) {
			System.out.println("Failed to send date message  to RabbitMQ: " + e.getMessage());
		}
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
	
	public Medicalrecord updatePayments(UUID id, UUID staffId) {
		Optional<Medicalrecord> record = medicalrecordRepository.findById(id);
		if (record.isPresent()) {
			Medicalrecord UpdateRecord = record.get();
			UpdateRecord.setPayments(true);
			UpdateRecord.setStaffId(staffId);
			// Cập nhật cho đơn thuốc
			prescriptionServiceClient.updatePrescriptionPaymentsById(UpdateRecord.getPrescriptionId());
			return medicalrecordRepository.save(UpdateRecord);
		}
		else
		{
			throw new RuntimeException("No medical record is found for update payments with ID: " + id);
		}
	}
}
