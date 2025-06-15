package com.example.treatmentservice.controller;

import com.example.treatmentservice.model.Treatment;
import com.example.treatmentservice.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/treatments")
@CrossOrigin(origins = "http://localhost")
public class TreatmentController {
    @Autowired
    private TreatmentService treatmentService;

    @GetMapping("/search")
    public ResponseEntity<List<Treatment>> searchTreatment(@RequestParam String name) {
        List<Treatment> treatments = treatmentService.searchTreatmentsByName(name);
        if (treatments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(treatments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Treatment> getTreatmentById(@PathVariable UUID id) {
    	Treatment treatment = treatmentService.getTreatmentById(id);
    	return new ResponseEntity<>(treatment, HttpStatus.OK);
    }
}