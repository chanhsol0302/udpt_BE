package com.example.doctorservice.repository;

import com.example.doctorservice.model.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PharmacistRepository extends JpaRepository<Pharmacist, UUID> {
	Optional<Pharmacist> findByUserId(UUID userId);
}