package com.example.reportservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reportservice.model.MedicalrecordReport;
import com.example.reportservice.repository.MedicalrecordRepository;

@Service
public class MedicalrecordCountService {
	@Autowired
	private MedicalrecordRepository medicalrecordRepo;
	
	public void updateMedicalrecordCount(String date) {
		Optional<MedicalrecordReport> existingReport = medicalrecordRepo.findByDate(date);
		if (existingReport.isPresent()) {
			MedicalrecordReport updateReport = existingReport.get();
			updateReport.setMedicalrecordCount(updateReport.getMedicalrecordCount() + 1);
			medicalrecordRepo.save(updateReport);
		}
		else
		{
			MedicalrecordReport updateReport = new MedicalrecordReport();
			updateReport.setDate(date);
			updateReport.setMedicalrecordCount(1);
			medicalrecordRepo.save(updateReport);
		}
	}
	
	public List<MedicalrecordReport> getAllReport() {
		return medicalrecordRepo.findAll();
	}
}