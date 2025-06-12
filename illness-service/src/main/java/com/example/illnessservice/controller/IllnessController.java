package com.example.illnessservice.controller;

import com.example.illnessservice.model.Illness;
import com.example.illnessservice.service.IllnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

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
}