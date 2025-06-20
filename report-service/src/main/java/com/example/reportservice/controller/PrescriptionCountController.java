package com.example.reportservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reportservice.model.PrescriptionReport;
import com.example.reportservice.service.PrescriptionCountService;

@RestController
@RequestMapping("api/reports")
@CrossOrigin(origins = "*")
public class PrescriptionCountController {
	@Autowired
	PrescriptionCountService countService;
	
	@GetMapping("/prescription")
	public ResponseEntity<List<PrescriptionReport>> getAllReport() {
		List<PrescriptionReport> reports = countService.getAllReport();
		return new ResponseEntity<>(reports, HttpStatus.OK);
	}
}