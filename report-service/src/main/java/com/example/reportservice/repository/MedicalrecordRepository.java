package com.example.reportservice.repository;

import com.example.reportservice.model.MedicalrecordReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface MedicalrecordRepository extends MongoRepository<MedicalrecordReport, String> {
    Optional<MedicalrecordReport> findByDate(String date);
}