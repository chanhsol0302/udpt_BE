package com.example.drugInventoryservice.service;

import com.example.drugInventoryservice.model.DrugInventory;
import com.example.drugInventoryservice.repository.DrugInventoryRepository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.List;

@Service
public class DrugInventoryService {
	@Autowired
	private DrugInventoryRepository drugInventoryRepository;
	
	public DrugInventory getDrugInventoryById(UUID id) {
		return drugInventoryRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Drug Inventory not found with ID: " + id));
	}
	
	public List<DrugInventory> searchDrugInventoriesByMedicineName(String medicineName) {
		return drugInventoryRepository.findByMedicine_NameContainingIgnoreCase(medicineName);
	}
	
	@Transactional
	public DrugInventory updateAvailableQuantity(UUID medicineId, int quantity) {
		DrugInventory drugInventory = drugInventoryRepository.findById(medicineId)
				.orElseThrow(() -> new IllegalArgumentException("Medicine not found with ID: " + medicineId));
		
		int available_quantity = drugInventory.getAvailableQuantity();
		drugInventory.setAvailableQuantity(available_quantity - quantity);
		return drugInventory;
	}
}