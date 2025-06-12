package com.example.treatmentservice.controller;

import com.example.treatmentservice.model.Treatment;
import com.example.treatmentservice.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/api/treatments")
@CrossOrigin(origins = "http://localhost")
public class TreatmentController {
    @Autowired
    private TreatmentService illnessService;

    @GetMapping("/search")
    public ResponseEntity<List<Treatment>> searchTreatment(@RequestParam String name) {
        List<Treatment> treatments = illnessService.searchTreatmentsByName(name);
        if (treatments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(treatments, HttpStatus.OK);
    }
}