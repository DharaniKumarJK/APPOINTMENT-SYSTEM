package com.example.appointmnentmanagementsystem.repository;

import com.example.appointmnentmanagementsystem.model.DoctorAvailability;
import com.example.appointmnentmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {
    List<DoctorAvailability> findByDoctor(User doctor);
}
