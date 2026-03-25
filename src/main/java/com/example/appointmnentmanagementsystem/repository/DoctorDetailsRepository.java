package com.example.appointmnentmanagementsystem.repository;

import com.example.appointmnentmanagementsystem.model.DoctorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorDetailsRepository extends JpaRepository<DoctorDetails, Long> {
}
