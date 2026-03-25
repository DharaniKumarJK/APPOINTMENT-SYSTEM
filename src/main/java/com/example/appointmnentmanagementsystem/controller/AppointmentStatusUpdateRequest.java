package com.example.appointmnentmanagementsystem.controller;

import com.example.appointmnentmanagementsystem.model.AppointmentStatus;

public record AppointmentStatusUpdateRequest(
    AppointmentStatus status
) {} 