package com.example.medicalrecordservice.repository;

import com.example.medicalrecordservice.model.Medicalrecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface MedicalrecordRepository extends MongoRepository<Medicalrecord, UUID> {

	List<Medicalrecord> findByPatientId(UUID patientId);

	List<Medicalrecord> findByDoctorId(UUID doctorId);

	Optional<Medicalrecord> findByAppointmentId(UUID appointmentId);
}