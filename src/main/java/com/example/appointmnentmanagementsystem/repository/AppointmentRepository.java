package com.example.appointmnentmanagementsystem.repository;

import com.example.appointmnentmanagementsystem.model.Appointment;
import com.example.appointmnentmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient(User patient);
    List<Appointment> findByDoctor(User doctor);
}
