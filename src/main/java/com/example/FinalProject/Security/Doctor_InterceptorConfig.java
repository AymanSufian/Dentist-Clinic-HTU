package com.example.FinalProject.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Component
public class Doctor_InterceptorConfig implements WebMvcConfigurer {

	@Autowired
	private Doctor_Service_Interceptor  interceptor ;
	
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor)
		.addPathPatterns("/**")
		.excludePathPatterns("/Doctor/RegisterDoctor")
		.excludePathPatterns("/Patient/RegisterPatient")
		.excludePathPatterns("/Patient/PatientLogin/login")
		.excludePathPatterns("/Patient/testPatient")
		.excludePathPatterns("/Doctor/DoctorTest/test")
		.excludePathPatterns("/Doctor/DoctorLogin/login");
		
	}
	
}
