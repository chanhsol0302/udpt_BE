package com.example.userservice.service;

import com.example.userservice.client.PatientServiceClient;
import com.example.userservice.dto.PatientCreateRequest; // Import dto và Feign Client
import com.example.userservice.dto.UserAuthenticateRequest;
import com.example.userservice.dto.UserRegisterRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.dto.UserUpdateRequest;
import com.example.userservice.model.User;
import com.example.userservice.model.UserRole;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

	// Khởi tạo Logger
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Inject Feign Client
    @Autowired
    private PatientServiceClient patientServiceClient;
    
    @Transactional // Đảm bảo giao dịch (quan trọng cho tính nhất quán)
    public UserResponse registerUser(UserRegisterRequest request) {
        // Kiểm tra email đã đăng ký
    	if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered.");
        }
    	
    	// Tạo và lưu User
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        // Đảm bảo chỉ PATIENT được tạo bản ghi trong Patient Service
        if (request.getRole() != UserRole.PATIENT) {
        	User savedUser = userRepository.save(user);
        	return convertToUserResponse(savedUser);
        }
        
        // Lưu user
        User savedUser = userRepository.save(user);
        
        // Chuẩn bị dữ liệu cho Patient Service
        PatientCreateRequest patientRequest = new PatientCreateRequest();
        patientRequest.setUserId(savedUser.getId());
        patientRequest.setName(request.getName());
        patientRequest.setDateOfBirth(request.getDateOfBirth());
        patientRequest.setGender(request.getGender());
        patientRequest.setAddress(request.getAddress());
        patientRequest.setPhone(request.getPhone());
        
        // Gửi dữ liệu đến Patient Service
        try {
        	patientServiceClient.createPatient(patientRequest);
        	log.info("Successfully sent patient data for user ID: {}", savedUser.getId());
        } catch (Exception e) {
        	log.error("Failed to send patient data to patient-service for user ID: {}. Error: {}", savedUser.getId(), e.getMessage());
        	throw new RuntimeException("Failed to register patient details. Please try again.", e);
        }
        return convertToUserResponse(savedUser);
    }

    public Optional<UserResponse> getUserById(UUID id) {
        return userRepository.findById(id).map(this::convertToUserResponse);
    }

    public UserResponse updateUser(UUID id, UserUpdateRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (!request.getEmail().equals(existingUser.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("Email already registered by another user.");
            }
            existingUser.setEmail(request.getEmail());
        }

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            existingUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getRole() != null) {
            existingUser.setRole(request.getRole());
        }

        User updatedUser = userRepository.save(existingUser);
        return convertToUserResponse(updatedUser);
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    public UserResponse authenticateUser(UserAuthenticateRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found.");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid password.");
        }

        // Nếu xác thực thành công, trả về UserResponse
        return convertToUserResponse(user);
    }
    
    private UserResponse convertToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}