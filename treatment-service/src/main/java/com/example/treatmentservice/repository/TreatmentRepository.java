package com.example.treatmentservice.repository;

import com.example.treatmentservice.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, UUID> {
	List<Treatment> findByNameContainingIgnoreCase(String name);
}