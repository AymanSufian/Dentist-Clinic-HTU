package com.example.FinalProject.DentistRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FinalProject.DentistEntity.PatientEntity;

@Repository
public interface PatientRepo extends JpaRepository<PatientEntity, Integer> {

	PatientEntity findByUsername(String username);

	
}
