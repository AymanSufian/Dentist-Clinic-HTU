package com.example.FinalProject.DentistEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;


@Entity
@Table(name = "Patient")
@Data
public class PatientEntity {
	
	
		@Column(name = "id")
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		 Integer idEntity ;
		
		
		@Column(name = "Patient_name")
		@NotBlank(message = "Can't Be Null !")
		private String name ;
		
		@Column(name = "username")
		@NotBlank(message = "Can't Be Null !")
		private String username ;
		
		@Column(name = "password")
		@NotBlank(message = "Can't Be Null !")
		private String password ;

		@Column(name = "phone_number")
		@Length(min = 10 , max = 10,message = "Phone numver must be exactly 10 numbers")
		@NotBlank(message = "Can't Be Null !")
		private String phone_number_entity ;
		
		@Column(name = "age")
		@Min(message = "Can't Be Less Than Zero !",value = 0)
		private Integer age_entity ;
		
		@Column(name = "gender")
		@NotBlank(message = "Can't Be Null !")
		private String gender_entity ;
		
		
			
			
		}



