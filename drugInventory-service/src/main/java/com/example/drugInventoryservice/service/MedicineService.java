package com.example.drugInventoryservice.service;

import com.example.drugInventoryservice.model.Medicine;
import com.example.drugInventoryservice.repository.MedicineRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class MedicineService {
	@Autowired
	private MedicineRepository medicineRepository;
	
	public List<Medicine> searchMedicinesByName(String query) {
		return medicineRepository.findByNameContainingIgnoreCase(query);
	}
}