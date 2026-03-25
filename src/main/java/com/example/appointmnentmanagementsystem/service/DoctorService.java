package com.example.appointmnentmanagementsystem.service;

import com.example.appointmnentmanagementsystem.controller.AppointmentStatusUpdateRequest;
import com.example.appointmnentmanagementsystem.controller.DoctorAvailabilityRequest;
import com.example.appointmnentmanagementsystem.model.*;
import com.example.appointmnentmanagementsystem.repository.AppointmentRepository;
import com.example.appointmnentmanagementsystem.repository.DoctorAvailabilityRepository;
import com.example.appointmnentmanagementsystem.repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorAvailabilityRepository availabilityRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public void setAvailability(User doctor, DoctorAvailabilityRequest request) {
        // Check if availability already exists for this date
        List<DoctorAvailability> existing = availabilityRepository.findByDoctor(doctor).stream()
            .filter(a -> a.getDate().equals(request.date()))
            .toList();

        DoctorAvailability avail;
        if (!existing.isEmpty()) {
            // Update existing
            avail = existing.get(0);
            avail.setStartTime(request.startTime());
            avail.setEndTime(request.endTime());
            availabilityRepository.save(avail);
            // Delete old slots
            List<TimeSlot> oldSlots = timeSlotRepository.findByAvailability(avail);
            timeSlotRepository.deleteAll(oldSlots);
        } else {
            // Create new
            avail = DoctorAvailability.builder()
                .doctor(doctor)
                .date(request.date())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .build();
            availabilityRepository.save(avail);
        }

        // Generate time slots every 30 minutes
        LocalTime current = request.startTime();
        while (current.isBefore(request.endTime())) {
            LocalTime endSlot = current.plusMinutes(30);
            if (endSlot.isAfter(request.endTime())) break;

            TimeSlot slot = TimeSlot.builder()
                .availability(avail)
                .slotStartTime(current)
                .slotEndTime(endSlot)
                .build();
            timeSlotRepository.save(slot);

            current = endSlot;
        }
    }

    public List<Appointment> getMyAppointments(User doctor) {
        return appointmentRepository.findByDoctor(doctor);
    }

    @Transactional
    public void updateAppointmentStatus(User doctor, Long appointmentId, AppointmentStatusUpdateRequest request) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appointment.getDoctor().equals(doctor)) {
            throw new RuntimeException("Unauthorized");
        }

        appointment.setStatus(request.status());
        appointmentRepository.save(appointment);

        // If cancelled, free the slot
        if (request.status() == AppointmentStatus.CANCELLED) {
            appointment.getTimeSlot().setStatus(TimeSlotStatus.AVAILABLE);
            timeSlotRepository.save(appointment.getTimeSlot());
        }
    }
}