package com.example.appointmnentmanagementsystem.controller;

import java.time.LocalDate;

public record DoctorAvailabilityRequest(
    LocalDate date,
    String startTime,
    String endTime
) {}