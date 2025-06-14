package com.example.medicalrecordservice.repository;

import com.example.medicalrecordservice.model.Medicalrecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicalrecordRepository extends MongoRepository<Medicalrecord, UUID> {
}