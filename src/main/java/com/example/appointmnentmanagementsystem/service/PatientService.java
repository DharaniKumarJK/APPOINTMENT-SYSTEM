package com.example.appointmnentmanagementsystem.service;

import com.example.appointmnentmanagementsystem.model.*;
import com.example.appointmnentmanagementsystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final UserRepository userRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorAvailabilityRepository availabilityRepository;

    public List<User> getAllDoctors() {
        return userRepository.findByRole(Role.DOCTOR);
    }

    public List<TimeSlot> getDoctorAvailability(Long doctorId) {
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        
        List<DoctorAvailability> availabilities = availabilityRepository.findByDoctor(doctor);
        return availabilities.stream()
                .flatMap(avail -> timeSlotRepository.findByAvailability(avail).stream())
                .filter(slot -> slot.getStatus() == TimeSlotStatus.AVAILABLE)
                .toList();
    }

    @Transactional
    public Appointment bookAppointment(User patient, Long timeSlotId) {
        TimeSlot slot = timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new RuntimeException("Time slot not found"));

        if (slot.getStatus() != TimeSlotStatus.AVAILABLE) {
            throw new RuntimeException("Time slot is not available");
        }

        slot.setStatus(TimeSlotStatus.BOOKED);
        timeSlotRepository.save(slot);

        Appointment appointment = Appointment.builder()
                .doctor(slot.getAvailability().getDoctor())
                .patient(patient)
                .timeSlot(slot)
                .status(AppointmentStatus.BOOKED)
                .build();

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getMyAppointments(User patient) {
        return appointmentRepository.findByPatient(patient);
    }

    @Transactional
    public void cancelAppointment(User patient, Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appointment.getPatient().equals(patient)) {
            throw new RuntimeException("Unauthorized");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);

        TimeSlot slot = appointment.getTimeSlot();
        slot.setStatus(TimeSlotStatus.AVAILABLE);
        timeSlotRepository.save(slot);
    }
}
