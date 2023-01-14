package com.example.FinalProject.DentistRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FinalProject.DentistEntity.DoctorEntity;

@Repository
public interface DoctorRepo extends JpaRepository<DoctorEntity, Integer> {

	
	DoctorEntity findByUsername(String username);

	
}
