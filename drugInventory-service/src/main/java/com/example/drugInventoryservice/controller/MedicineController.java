package com.example.drugInventoryservice.controller;

import com.example.drugInventoryservice.model.Medicine;
import com.example.drugInventoryservice.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
@CrossOrigin(origins = "http://localhost")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;

    @GetMapping("/search")
    public ResponseEntity<List<Medicine>> searchMedicine(@RequestParam String name) {
        List<Medicine> medicines = medicineService.searchMedicinesByName(name);
        if (medicines.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(medicines, HttpStatus.OK);
    }
}