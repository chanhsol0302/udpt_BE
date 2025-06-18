package com.example.doctorservice.controller;

import com.example.doctorservice.service.StaffService;
import com.example.doctorservice.model.Staff;

import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.UUID;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/staffs")
@CrossOrigin(origins = "http://localhost")
public class StaffController {
	@Autowired
    private StaffService staffService;

	@GetMapping("/{userId}")
	public ResponseEntity<Staff> getStaffByUserId(@PathVariable UUID userId) {
		Staff staff = staffService.getStaffByUserId(userId);
		return new ResponseEntity<>(staff, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Staff> getStaffById(@PathVariable UUID id) {
		Staff staff = staffService.getStaffById(id);
		return new ResponseEntity<>(staff, HttpStatus.OK);
	}
	
}