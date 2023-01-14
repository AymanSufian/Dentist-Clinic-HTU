package com.example.FinalProject.DentistEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Entity
@Table(name = "Doctor")
@Data
public class DoctorEntity {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idEntity ;
	
	
	
	@NotBlank(message = "Can't Be Null !")
	@Column(name = "name")
	private String name ;
	
	@NotBlank(message = "Can't Be Null !")
	@Column(name = "username")
	private String username ;
	
	@NotBlank(message = "Can't Be Null !")
	@Column(name = "password")
	private String password ;

	@NotBlank(message = "Can't Be Null !")
	@Column(name = "Doctor_speciality")
	private String doctor_speciality ;


	@Column(name = "national_id")
	private Integer national_id ;
	
	@Length(min = 10 , max = 10,message = "Phone number must be exactly 10 numbers")
	@NotBlank(message = "Can't Be Null !")
	@Column(name = "phone_number")
	private String phone_number ;
	
	
}
