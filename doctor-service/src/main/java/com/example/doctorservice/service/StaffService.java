package com.example.doctorservice.service;

import com.example.doctorservice.model.Staff;
import com.example.doctorservice.repository.StaffRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Service
public class StaffService {
	@Autowired
	private StaffRepository staffRepository;
	
	public Staff getStaffByUserId(UUID userId) {
		return staffRepository.findByUserId(userId)
				.orElseThrow(() -> new RuntimeException("No staff is found with userID: " + userId));
	}
}