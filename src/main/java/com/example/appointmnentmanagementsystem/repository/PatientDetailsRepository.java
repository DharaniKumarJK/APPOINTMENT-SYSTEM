package com.example.appointmnentmanagementsystem.repository;

import com.example.appointmnentmanagementsystem.model.PatientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientDetailsRepository extends JpaRepository<PatientDetails, Long> {
}
