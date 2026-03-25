package com.example.appointmnentmanagementsystem.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientRegisterRequest {
    private String name;
    private String email;
    private String password;
    private Integer age;
    private String gender;
}
