package com.example.drugInventoryservice.repository;

import com.example.drugInventoryservice.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, UUID> {
	List<Medicine> findByNameContainingIgnoreCase(String name);
}