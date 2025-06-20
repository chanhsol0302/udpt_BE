package com.example.reportservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reportservice.model.PrescriptionReport;
import com.example.reportservice.repository.PrescriptionRepository;

@Service
public class PrescriptionCountService {
	@Autowired
	private PrescriptionRepository prescriptionRepo;
	
	public void updatePrescriptionCount(String date) {
		Optional<PrescriptionReport> existingReport = prescriptionRepo.findByDate(date);
		if (existingReport.isPresent()) {
			PrescriptionReport updateReport = existingReport.get();
			updateReport.setPrescriptionCount(updateReport.getPrescriptionCount() + 1);
			prescriptionRepo.save(updateReport);
		}
		else
		{
			PrescriptionReport updateReport = new PrescriptionReport();
			updateReport.setDate(date);
			updateReport.setPrescriptionCount(1);
			prescriptionRepo.save(updateReport);
		}
	}
	
	public List<PrescriptionReport> getAllReport() {
		return prescriptionRepo.findAll();
	}
}