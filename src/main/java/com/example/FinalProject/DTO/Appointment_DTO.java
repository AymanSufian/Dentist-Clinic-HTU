package com.example.FinalProject.DTO;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment_DTO {
	
	
	
	private LocalTime time ;
	
	private Integer patient_id ;
	
	private String patient_name ;
	
	
	
	
	
	

}
