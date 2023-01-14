package com.example.FinalProject.DentistEntity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Table(name = "Doctor_Patient")
@Data
public class AppointmentEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer appointmentId ;
	
	@Column(name = "doctor_id")
	private Integer doctor_id ;
	
	
	@Column(name = "patient_id")
	private Integer patient_id ;
	
	
	
	@Column(name = "status")
	private Integer status ;
	
	
	@JsonFormat( pattern = "dd/MM/yyyy")
	@Column(name = "date")
	LocalDate date;
	
	
	
	@JsonFormat( pattern = "HH:mm")
	@Column(name = "time")
	LocalTime time;
	
}
