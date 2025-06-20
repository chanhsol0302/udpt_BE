package com.example.reportservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.reportservice.model.PrescriptionReport;

import java.util.Optional;

public interface PrescriptionRepository extends MongoRepository<PrescriptionReport, String> {
    Optional<PrescriptionReport> findByDate(String date);
}