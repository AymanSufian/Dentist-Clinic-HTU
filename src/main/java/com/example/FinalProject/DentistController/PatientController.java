package com.example.FinalProject.DentistController;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.example.FinalProject.DentistEntity.AppointmentEntity;
import com.example.FinalProject.DentistEntity.DoctorEntity;
import com.example.FinalProject.DentistEntity.PatientEntity;
import com.example.FinalProject.DentistService.PatientService;
import com.example.FinalProject.POJO.PatientLogin;
import com.example.FinalProject.POJO.ResultPojo;

@RestController
@RequestMapping("/Patient")
public class PatientController {
	
	
	@Autowired
	private PatientService service ;
	
	
	
	
	
	@PostMapping("/PatientLogin/login")
	public ResultPojo login(@Valid @RequestBody PatientLogin login) {

		return service.Patientlogin(login);
		
	}

	@GetMapping("/testPatient")
	public void testPatient(HttpServletRequest request, HttpServletResponse response) {
		
		String token = request.getHeader("token");
		
		System.out.println(token);
		
	}
	
	
	
	
	
	
	@PostMapping("/RegisterPatient")
	public ResultPojo RegisterPatient ( HttpServletRequest request, HttpServletResponse response,
									    @Valid @RequestBody PatientEntity Patient) {
				
		
			return service.RegisterPatient(Patient);
		
		}
	
	
	@PutMapping("/UpdatePatient")
	public ResultPojo UpdatePatient ( HttpServletRequest request, HttpServletResponse response,
									  @Valid @RequestBody PatientEntity UpdatePatient) {


			return service.RegisterPatient(UpdatePatient);

		}
	
	
	@GetMapping("/findListOfAllDoctors")
	public List<DoctorEntity> findallDoctors() {
		
		return service.findallDoctorss();
	}
	
	
	
	
	@PostMapping("/CreateAppointment")
	
	public ResultPojo CreateAppointment ( HttpServletRequest request, HttpServletResponse response, 
									   @Valid @RequestBody AppointmentEntity Patient) {
	
	
			return service.CreateOrUpdateAnAppointment(Patient);
		
		} 
	
	
	
	@PutMapping("/UpdateAppointment")
	
	public ResultPojo UpdateAppointment ( HttpServletRequest request, HttpServletResponse response, 
									   @Valid @RequestBody AppointmentEntity Patient) {
		
		
		
		//if statement
	
			return service.CreateOrUpdateAnAppointment(Patient);
		
		} 
	
	
	
	
	
	@DeleteMapping("/deleteAppointmentById")
	public ResultPojo deleteAppointmentById (HttpServletRequest request, HttpServletResponse response,
									   @RequestParam Integer AppointmentId) {
				
		return service.deleteAppointmentById(AppointmentId);
		
		}
	
	
	@GetMapping("/getAvailableTimesForDoctors")
	public List<LocalTime> getAvailableTimes(Integer doctorId) {
		
		return service.getAvailableTimesForDoctors(doctorId);
	}
	
	
	
	
	 @GetMapping("/CheckTimelines")
	  public File CheckAllTimeLiens(@RequestParam("from") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate from ,
			  				 @RequestParam("to") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate to ,
			  				 @RequestParam ("patient_id") Integer patient_id) throws IOException 
	  
	  {
		  
		  		return service.CheckAllTimeLiens(from, to, patient_id);

	  
	  }
	  
	
	
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResultPojo handleValidationExceptions(MethodArgumentNotValidException ex) {
		
		ResultPojo result = new ResultPojo();
		
		result.setStatusCode("Failed");

		Map<String, Object> errors = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error) -> {
		
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		
		});
		
		result.setResultMap(errors);
		return result;

	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResultPojo handleAllExceptionMethod(Exception ex, WebRequest requset, HttpServletResponse res) {
		
		ResultPojo result = new ResultPojo();
		
		result.setStatusCode("Failed");

		Map<String, Object> errors = new HashMap<>();
		
		errors.put("Exception", ex.getMessage());
		result.setResultMap(errors);
		return result;
	}
	
	
	
	

}
