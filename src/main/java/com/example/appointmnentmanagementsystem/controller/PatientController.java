package com.example.appointmnentmanagementsystem.controller;

import com.example.appointmnentmanagementsystem.model.Appointment;
import com.example.appointmnentmanagementsystem.model.TimeSlot;
import com.example.appointmnentmanagementsystem.model.User;
import com.example.appointmnentmanagementsystem.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/doctors")
    public ResponseEntity<List<User>> getAllDoctors() {
        return ResponseEntity.ok(patientService.getAllDoctors());
    }

    @GetMapping("/doctors/{id}/availability")
    public ResponseEntity<List<TimeSlot>> getDoctorAvailability(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getDoctorAvailability(id));
    }

    @PostMapping("/appointments")
    public ResponseEntity<Appointment> bookAppointment(@RequestBody AppointmentBookingRequest request) {
        User patient = getCurrentUser();
        return ResponseEntity.ok(patientService.bookAppointment(patient, request.timeSlotId()));
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getMyAppointments() {
        User patient = getCurrentUser();
        return ResponseEntity.ok(patientService.getMyAppointments(patient));
    }

    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long id) {
        User patient = getCurrentUser();
        patientService.cancelAppointment(patient, id);
        return ResponseEntity.ok("Appointment cancelled successfully");
    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
