package com.example.drugInventoryservice.repository;

import com.example.drugInventoryservice.model.DrugInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface DrugInventoryRepository extends JpaRepository<DrugInventory, UUID> {
}