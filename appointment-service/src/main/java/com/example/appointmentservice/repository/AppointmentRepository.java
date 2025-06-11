package com.example.appointmentservice.repository;

import com.example.appointmentservice.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
	List<Appointment> findByPatientId(UUID patientId);
	
	// Thêm phương thức để đếm theo ca và lọc
	@Query("SELECT a.appointmentShift "
		+ "FROM Appointment a "
		+ "WHERE a.doctorId = :doctorId "
		+ "AND a.specialtyId = :specialtyId "
		+ "AND a.appointmentDate = :appointmentDate "
		+ "GROUP BY a.appointmentShift HAVING COUNT(a) >= 10")
	List<Integer> findHighDemandAppointmentShifts(
			@Param("doctorId") UUID doctorId,
			@Param("specialtyId") UUID specialtyId,
			@Param("appointmentDate") LocalDate appointmentDate);
	
	List<Appointment> findByDoctorIdAndAppointmentDate(UUID doctorId, LocalDate appointmentDate);
	
	// Đếm số lượng lịch hẹn theo bác sĩ, ngày và ca
    Integer countByDoctorIdAndAppointmentDateAndAppointmentShift(UUID doctorId, LocalDate appointmentDate, Integer appointmentShift);
}