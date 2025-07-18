package com.example.doctorservice.repository;

import com.example.doctorservice.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
	List<Doctor> findBySpecialtyId(UUID specialty_id);
	
	Optional<Doctor> findByUserId(UUID userId);
}