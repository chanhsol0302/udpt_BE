package com.example.drugInventoryservice.controller;

import com.example.drugInventoryservice.model.DrugInventory;
import com.example.drugInventoryservice.service.DrugInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/drugInventory")
@CrossOrigin(origins = "http://localhost")
public class DrugInventoryController {
    @Autowired
    private DrugInventoryService drugInventoryService;

    @GetMapping("/{id}")
    public ResponseEntity<DrugInventory> getDrugInventoryById(@PathVariable UUID id) {
    	try {
    		DrugInventory drugInventory = drugInventoryService.getDrugInventoryById(id);
    		return new ResponseEntity<>(drugInventory, HttpStatus.OK);
    	}
    	catch (IllegalArgumentException ex){
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }
    
    @GetMapping("/searchByMedicineName")
    public ResponseEntity<List<DrugInventory>> searchDrugInventoryByMedicineName(@RequestParam String name) {
        List<DrugInventory> drugInventories = drugInventoryService.searchDrugInventoriesByMedicineName(name);

        if (drugInventories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(drugInventories, HttpStatus.OK);
    }
    
    @PutMapping("/updateQuantity")
    public ResponseEntity<DrugInventory> updateAvailableQuantity(@RequestParam UUID id, int quantity) {
        DrugInventory drugInventory = drugInventoryService.updateAvailableQuantity(id, quantity);
        return new ResponseEntity<>(drugInventory, HttpStatus.OK);
    }
}