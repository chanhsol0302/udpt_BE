package com.example.prescriptionservice.repository;

import com.example.prescriptionservice.model.Prescription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface PrescriptionRepository extends MongoRepository<Prescription, UUID> {
	List<Prescription> findByCreatedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}