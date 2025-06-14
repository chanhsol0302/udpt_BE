package com.example.illnessservice.controller;

import com.example.illnessservice.model.Illness;
import com.example.illnessservice.service.IllnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/illnesses")
@CrossOrigin(origins = "http://localhost")
public class IllnessController {
    @Autowired
    private IllnessService illnessService;

    @GetMapping("/search")
    public ResponseEntity<List<Illness>> searchIllness(@RequestParam String name) {
        List<Illness> illnesses = illnessService.searchIllnessesByName(name);
        if (illnesses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(illnesses, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Illness> getIllnessById(@PathVariable UUID id) {
    	Illness illness = null;
    	try {
    		illness = illnessService.getIllnessById(id);
    	}
    	catch (RuntimeException e) {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    	}
    	return new ResponseEntity<>(illness, HttpStatus.OK);
    }
}