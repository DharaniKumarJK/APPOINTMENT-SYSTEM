package com.example.appointmnentmanagementsystem.controller;

import com.example.appointmnentmanagementsystem.model.Appointment;
import com.example.appointmnentmanagementsystem.model.User;
import com.example.appointmnentmanagementsystem.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/availability")
    public ResponseEntity<String> setAvailability(@RequestBody DoctorAvailabilityRequest request) {
        User doctor = getCurrentUser();
        doctorService.setAvailability(doctor, request);
        return ResponseEntity.ok("Availability set successfully");
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getMyAppointments() {
        User doctor = getCurrentUser();
        List<Appointment> appointments = doctorService.getMyAppointments(doctor);
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/appointments/{id}/status")
    public ResponseEntity<String> updateAppointmentStatus(@PathVariable Long id, @RequestBody AppointmentStatusUpdateRequest request) {
        User doctor = getCurrentUser();
        doctorService.updateAppointmentStatus(doctor, id, request);
        return ResponseEntity.ok("Appointment status updated");
    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
