package com.example.appointmentservice.service;

import com.example.appointmentservice.model.Appointment;
import com.example.appointmentservice.repository.AppointmentRepository;
import com.example.appointmentservice.dto.AppointmentCreateRequest;
import com.example.appointmentservice.dto.AppointmentDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;

import java.util.stream.Collectors;

@Service
public class AppointmentService {
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	// Đặt lịch (Trả về dto)
	@Transactional
    public AppointmentDTO createAppointment(AppointmentCreateRequest request) {
		Appointment appointment = new Appointment();
        appointment.setPatientId(request.getPatient_id());
        appointment.setDoctorId(request.getDoctor_id());
        appointment.setSpecialtyId(request.getSpecialty_id());
        appointment.setPatientName(request.getPatient_name());
        appointment.setDoctorName(request.getDoctor_name());
        appointment.setSpecialtyName(request.getSpecialty_name());
        appointment.setAppointmentDate(request.getAppointment_date());
        appointment.setAppointmentShift(request.getAppointment_shift());

        // Tính ticket_number
        Integer existingAppointmentsCount = appointmentRepository.countByDoctorIdAndAppointmentDateAndAppointmentShift(
        		request.getDoctor_id(), 
        		request.getAppointment_date(), 
        		request.getAppointment_shift()
        );
        appointment.setTicketNumber(existingAppointmentsCount.intValue() + 1);
        
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return convertToAppointmentDTO(savedAppointment);
    }
	// Xem lịch theo bệnh nhân
	public List<AppointmentDTO> getAppointmentsByPatientId(UUID patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        return appointments.stream()
        		.map(this::convertToAppointmentDTO)
        		.collect(Collectors.toList());
    }
	// Update status
	// Xem lịch theo ngày + bác sĩ
	// Danh sách ca dưới 10 ngày
	public List<Integer> getHighDemandAppointmentShifts(
			UUID doctorId, 
			UUID specialtyId, 
			LocalDate appointmentDate) {
        return appointmentRepository.findHighDemandAppointmentShifts(doctorId, specialtyId, appointmentDate);
    }
	
	@Transactional
    public AppointmentDTO updateAppointmentStatus(UUID id, Integer newStatus) {
        // Tìm cuộc hẹn theo ID
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);

        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            // Cập nhật trạng thái mới
            appointment.setStatus(newStatus);
            // Lưu lại vào database
            Appointment updatedAppointment = appointmentRepository.save(appointment);
            // Chuyển đổi sang DTO và trả về
            return convertToAppointmentDTO(updatedAppointment);
        }
        else
        {
        	throw new RuntimeException("No appointment is found with id:" + id);
        }
    }
	
	public List<AppointmentDTO> getAppointmentsByDoctorIdAndDate(UUID doctorId, LocalDate appointmentDate) {
        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId, appointmentDate);
        return appointments.stream()
                .map(this::convertToAppointmentDTO)
                .collect(Collectors.toList());
    }
	
	public List<AppointmentDTO> getAppointmentsByDate(LocalDate appointmentDate) {
        List<Appointment> appointments = appointmentRepository.findByAppointmentDate(appointmentDate);
        return appointments.stream()
                .map(this::convertToAppointmentDTO)
                .collect(Collectors.toList());
    }
	
	
	private AppointmentDTO convertToAppointmentDTO(Appointment appointment) {
        AppointmentDTO response = new AppointmentDTO();
        response.setId(appointment.getId());
        response.setPatient_id(appointment.getPatientId());
        response.setDoctor_id(appointment.getDoctorId());
        response.setSpecialty_id(appointment.getSpecialtyId());
        response.setPatient_name(appointment.getPatientName());
        response.setDoctor_name(appointment.getDoctorName());
        response.setSpecialty_name(appointment.getSpecialtyName());
        response.setAppointment_date(appointment.getAppointmentDate());
        response.setAppointment_shift(appointment.getAppointmentShift());
        response.setTicket_number(appointment.getTicketNumber());
        response.setStatus(appointment.getStatus());
        return response;
    }
}