package com.example.appointmnentmanagementsystem.controller;

import java.time.LocalDate;
import java.time.LocalTime;

public record DoctorAvailabilityRequest(
    LocalDate date,
    LocalTime startTime,
    LocalTime endTime
) {} 