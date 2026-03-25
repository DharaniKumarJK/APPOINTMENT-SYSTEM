package com.example.appointmnentmanagementsystem.security;

import com.example.appointmnentmanagementsystem.model.DoctorDetails;
import com.example.appointmnentmanagementsystem.model.PatientDetails;
import com.example.appointmnentmanagementsystem.model.Role;
import com.example.appointmnentmanagementsystem.model.User;
import com.example.appointmnentmanagementsystem.repository.DoctorDetailsRepository;
import com.example.appointmnentmanagementsystem.repository.PatientDetailsRepository;
import com.example.appointmnentmanagementsystem.repository.UserRepository;
import com.example.appointmnentmanagementsystem.security.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final DoctorDetailsRepository doctorDetailsRepository;
    private final PatientDetailsRepository patientDetailsRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public RegisterResponse registerAdmin(AdminRegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);
        return RegisterResponse.builder()
                .message("Admin registered successfully")
                .email(user.getEmail())
                .build();
    }

    @Transactional
    public RegisterResponse registerDoctor(DoctorRegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.DOCTOR)
                .build();
        var savedUser = userRepository.save(user);

        var doctorDetails = DoctorDetails.builder()
                .user(savedUser)
                .specialization(request.getSpecialization())
                .experienceYears(request.getExperienceYears())
                .build();
        doctorDetailsRepository.save(doctorDetails);

        return RegisterResponse.builder()
                .message("Doctor registered successfully")
                .email(savedUser.getEmail())
                .build();
    }

    @Transactional
    public RegisterResponse registerPatient(PatientRegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.PATIENT)
                .build();
        var savedUser = userRepository.save(user);

        var patientDetails = PatientDetails.builder()
                .user(savedUser)
                .age(request.getAge())
                .gender(request.getGender())
                .build();
        patientDetailsRepository.save(patientDetails);

        return RegisterResponse.builder()
                .message("Patient registered successfully")
                .email(savedUser.getEmail())
                .build();
    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
