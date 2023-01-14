package com.example.FinalProject.DentistController;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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
import com.example.FinalProject.DentistService.DoctorService;
import com.example.FinalProject.POJO.DoctorLogin;
import com.example.FinalProject.POJO.ResultPojo;


@RestController
@RequestMapping("/Doctor")
public class DoctorController {

	
	@Autowired
	private DoctorService service ;

	
	
	@PostMapping("/DoctorLogin/login")
	public ResultPojo login(@Valid @RequestBody DoctorLogin login) {

		return service.Doctorlogin(login);
		
	}

	@GetMapping("/DoctorTest/test")
	public void test(HttpServletRequest request, HttpServletResponse response) {
		
		String token = request.getHeader("token");
		
		System.out.println(token);
		
	}

	
	
	
	
	@PostMapping("/RegisterDoctor")
	
	public ResultPojo RegisterDoctor ( HttpServletRequest request, HttpServletResponse response, 
									   @Valid @RequestBody DoctorEntity Doctor) {
		
	
			return service.RegisterDoctor(Doctor);
		
		} 
	
	
	@PutMapping("/UpdateDoctor")
	
	public ResultPojo UpdateDoctor ( HttpServletRequest request, HttpServletResponse response, 
									   @Valid @RequestBody DoctorEntity Doctor) {
		
	
			return service.UpdateDoctor(Doctor);
		
		} 
		
	
	
	@GetMapping("/findListOfAllPatients")
	public List<PatientEntity> findallPatient() {
		
		return service.findallPatients();
	}
	
	
	@GetMapping("/getAvailableTimes")
	public ResultPojo getAvailableTimes(Integer doctorId) {
		
		return service.getAvailableTimes(doctorId);
	}
	
	
	
	@GetMapping("/getBookedTimes")
	public ResultPojo getAllBookedTimes(Integer doctorId) {
		
		return service.getAllBookedTimes(doctorId);
	}

	
	
	
	
	@GetMapping("/findPatientById")
	public ResultPojo findPatientById(Integer Id) {
		
			return service.findPatientById(Id);
		
	
	}
	
	
	
	
	
	
	
	@PostMapping("/CreateAppointmentForPatient")
	
	public ResultPojo CreateAppointmentForPatient ( HttpServletRequest request, HttpServletResponse response, 
													@Valid @RequestBody AppointmentEntity Patient) {
		
		
			return service.CreateAppointmentForPatient(Patient);
		
		} 

	
	
	@DeleteMapping("/deleteAppointmentForPatientById")
	public ResultPojo deleteAppointmentForPatientById (HttpServletRequest request, HttpServletResponse response,
									   @RequestParam Integer AppointmentId) {
				
		return service.deleteAppointmentForPatientById(AppointmentId);
		
		}
	
	
	
	
	
	@PutMapping("update-appointment-status")
    public ResultPojo updateAppointmentStatus(@RequestParam Integer appointmentId , @RequestParam  Integer status ) {
        
        return service.updateStatusById(appointmentId, status);
         
    }
	
	
	
	 @GetMapping("/count-visits")
	 public ResultPojo countVisitsByDoctorIdAndPatientId(@RequestParam Integer doctorId,@RequestParam Integer patientId) {
		 
		 
		 
	        return service.countVisitsByDoctorIdAndPatientId(doctorId, patientId);
	    
	 }
		  

	  
	  
	  @GetMapping("/generate-report")
	  public File getSummary(@RequestParam("from") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate from ,
			  				 @RequestParam("to") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate to ,
			  				 @RequestParam ("doctor_Id") Integer doctorId) throws IOException 
	  
	  {
		  
		  		return service.getSummary(from, to, doctorId);

	  
	  
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
