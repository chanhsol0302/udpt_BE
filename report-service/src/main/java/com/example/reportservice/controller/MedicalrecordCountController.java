package com.example.reportservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reportservice.model.MedicalrecordReport;
import com.example.reportservice.service.MedicalrecordCountService;

@RestController
@RequestMapping("api/reports")
@CrossOrigin(origins = "*")
public class MedicalrecordCountController {
	@Autowired
	private MedicalrecordCountService countService;
	
	@GetMapping("/medicalrecord")
	public ResponseEntity<List<MedicalrecordReport>> getAllReport() {
		List<MedicalrecordReport> reports = countService.getAllReport();
		return new ResponseEntity<>(reports, HttpStatus.OK);
	}
}