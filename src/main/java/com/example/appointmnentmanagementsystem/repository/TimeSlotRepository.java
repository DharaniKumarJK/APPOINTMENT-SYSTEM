package com.example.appointmnentmanagementsystem.repository;

import com.example.appointmnentmanagementsystem.model.DoctorAvailability;
import com.example.appointmnentmanagementsystem.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByAvailability(DoctorAvailability availability);
}
