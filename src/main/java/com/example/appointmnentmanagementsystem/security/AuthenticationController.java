package com.example.appointmnentmanagementsystem.security;

import com.example.appointmnentmanagementsystem.security.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register/admin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(
            @RequestBody AdminRegisterRequest request
    ) {
        return ResponseEntity.ok(service.registerAdmin(request));
    }

    @PostMapping("/register/doctor")
    public ResponseEntity<AuthenticationResponse> registerDoctor(
            @RequestBody DoctorRegisterRequest request
    ) {
        return ResponseEntity.ok(service.registerDoctor(request));
    }

    @PostMapping("/register/patient")
    public ResponseEntity<AuthenticationResponse> registerPatient(
            @RequestBody PatientRegisterRequest request
    ) {
        return ResponseEntity.ok(service.registerPatient(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
