package com.example.prescriptionservice.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "drugInventory-service", url = "http://localhost:8088")
public interface DrugInventoryServiceClient {
	@PutMapping("api/drugInventory/updateQuantity")
	void updateAvailableQuantity(@RequestParam UUID id,@RequestParam int quantity);
}

