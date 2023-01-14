package com.example.FinalProject.DTO;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.FinalProject.DentistEntity.AppointmentEntity;
import com.example.FinalProject.DentistRepository.PatientRepo;

@Component
public class AppointmentMapper {
	

	@Autowired
	PatientRepo patientRepo ;
	
	public Appointment_DTO appointment_DTO (AppointmentEntity appointmentEntity) {
		
		return Appointment_DTO.builder().time(appointmentEntity.getTime()).patient_id(appointmentEntity.getPatient_id())
				.patient_name(patientRepo.findById(appointmentEntity.getPatient_id()).get().getName()).build();
				
		
	}
	
	
	public List<Appointment_DTO> appointment (List<AppointmentEntity> DTO_list  ){
		return DTO_list.stream().map(x -> appointment_DTO(x)).collect(Collectors.toList());
		
	}

}
