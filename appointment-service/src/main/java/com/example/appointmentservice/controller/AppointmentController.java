package com.example.appointmentservice.controller;

import com.example.appointmentservice.dto.AppointmentCreateRequest;
import com.example.appointmentservice.dto.AppointmentDTO;
import com.example.appointmentservice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.UUID;
import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentCreateRequest request) {
        AppointmentDTO createdAppointment = appointmentService.createAppointment(request);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByPatientId(@PathVariable UUID patientId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByPatientId(patientId);
        if (appointments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/shifts")
    public ResponseEntity<List<Integer>> getHighDemandAppointmentShifts(
            @RequestParam("doctor_id") UUID doctorId,
            @RequestParam("specialty_id") UUID specialtyId,
            @RequestParam("appointment_date") LocalDate appointmentDate) {

        List<Integer> highDemandShifts = appointmentService.getHighDemandAppointmentShifts(doctorId, specialtyId, appointmentDate);
        
        if (highDemandShifts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
        }
        return new ResponseEntity<>(highDemandShifts, HttpStatus.OK);
    }
    
    @PutMapping("/updateStatus")
    public ResponseEntity<AppointmentDTO> updateAppointmentStatus(@RequestParam UUID id, @RequestParam Integer status) {
        AppointmentDTO updatedAppointment = appointmentService.updateAppointmentStatus(id, status);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }
    
    @GetMapping("/by-doctor-and-date")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDoctorAndDate(
            @RequestParam("doctor_id") UUID doctorId,
            @RequestParam("appointment_date") LocalDate appointmentDate) {

        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDoctorIdAndDate(doctorId, appointmentDate);

        if (appointments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
    
    @GetMapping("/byDate/{appointmentDate}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDate(
            @PathVariable LocalDate appointmentDate) {

        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDate(appointmentDate);

        if (appointments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
}