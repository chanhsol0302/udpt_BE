package com.example.doctorservice.service;

import com.example.doctorservice.model.Doctor;
import com.example.doctorservice.repository.DoctorRepository;
import com.example.doctorservice.dto.DoctorDTO;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import java.util.stream.Collectors;

@Service
public class DoctorService {
	@Autowired
	private DoctorRepository doctorRepository;
	
	public List<DoctorDTO> getDoctorsBySpecialtyId(UUID specialtyId) {
		List<Doctor> doctors = doctorRepository.findBySpecialtyId(specialtyId);
		return doctors.stream()
                .map(this::convertToDoctorDTO) 
                .collect(Collectors.toList());
	}
	
	// Yêu cầu trả về DoctorId khi đăng nhập (UserId)
	public Optional<DoctorDTO> getDoctorIdByUserId(UUID userId) {
		Optional<Doctor> doctor = doctorRepository.findByUserId(userId);
		return doctor.map(this::convertToDoctorDTO);
	}
		
	private DoctorDTO convertToDoctorDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setUserId(doctor.getUserId());
        if (doctor.getSpecialty() != null) {
            dto.setSpecialtyId(doctor.getSpecialty().getId());
            dto.setSpecialtyName(doctor.getSpecialty().getName());
        }
        return dto;
    }
}