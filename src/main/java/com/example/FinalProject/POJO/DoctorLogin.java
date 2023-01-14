package com.example.FinalProject.POJO;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class DoctorLogin {

	@NotBlank(message = "Username can't be null or empty !")
	public String username;
	
	@NotBlank(message = "Password can't be null or empty !")
	public String password;
	
}
