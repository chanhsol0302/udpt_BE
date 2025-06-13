package com.example.prescriptionservice.repository;

import com.example.prescriptionservice.model.Prescription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrescriptionRepository extends MongoRepository<Prescription, UUID> {
}