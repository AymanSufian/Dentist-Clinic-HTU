package com.example.FinalProject.DentistService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.FinalProject.DTO.AppointmentMapper;
import com.example.FinalProject.DentistEntity.AppointmentEntity;
import com.example.FinalProject.DentistEntity.DoctorEntity;
import com.example.FinalProject.DentistEntity.PatientEntity;
import com.example.FinalProject.DentistRepository.AppointmentRepo;
import com.example.FinalProject.DentistRepository.DoctorRepo;
import com.example.FinalProject.DentistRepository.PatientRepo;
import com.example.FinalProject.POJO.DoctorLogin;
import com.example.FinalProject.POJO.ResultPojo;
import com.example.FinalProject.TokenUtility.TokenUtility;



@Service
public class DoctorService {

	@Autowired
	private DoctorRepo DocRepo ;
	
	@Autowired
	private PatientRepo patientRepo ;
	
	@Autowired
	AppointmentRepo appointmentRepo ;
	
	@Autowired
	TokenUtility tokenUtility ;

	@Autowired
	AppointmentMapper appointmentMapper ;
	
	
	
	public ResultPojo Doctorlogin(DoctorLogin login) {
		
		ResultPojo result = new ResultPojo();
		
		Map<String, Object> mapResult = new HashMap<>();
		
		DoctorEntity user = DocRepo.findByUsername(login.getUsername());
		
		if (user == null ) {
		
			mapResult.put("username", "Sorry ! User Not Found ");
			result.setStatusCode("1");
			result.setStatusDescription("Faild");
			result.setResultMap(mapResult);
			
			return result;
		}
		
		if (!user.getPassword().equalsIgnoreCase(login.getPassword())) {

			mapResult.put("password", "Password Not Valid !");
			result.setStatusCode("1");
			result.setStatusDescription("Faild");
			result.setResultMap(mapResult);
			return result;

		}
		
		String token = tokenUtility.generateToken(login.getUsername());
		mapResult.put("token", token);
		result.setStatusCode("0");
		result.setStatusDescription("Success");
		result.setResultMap(mapResult);
		return result;
	
	}
	
	
	
	
	
	
	
	public ResultPojo RegisterDoctor(DoctorEntity AddDoctor) {
		
		ResultPojo result = new ResultPojo();
	
		Map<String, Object> mapResult = new HashMap<>();
		
		
		DocRepo.save(AddDoctor);
		result.setStatusCode("0");
		result.setStatusDescription("Success");
		mapResult.put("Success", "Doctor Added");
		result.setResultMap(mapResult);
		return result;

	}
	
	
	
	public ResultPojo UpdateDoctor(DoctorEntity AddDoctor) {
		
		ResultPojo result = new ResultPojo();
	
		Map<String, Object> mapResult = new HashMap<>();
		
		
		
		
		DocRepo.save(AddDoctor);
		result.setStatusCode("0");
		result.setStatusDescription("Success");
		mapResult.put("Success", "Doctor Added or updated Successfuly");
		result.setResultMap(mapResult);
		return result;

	}
	
	
	
	public List<PatientEntity> findallPatients() {
		
		return patientRepo.findAll();
	}
	
	
	
	
	public ResultPojo findPatientById(Integer id) {
		
		
		ResultPojo result = new ResultPojo();
		
		Map<String, Object> mapResult = new HashMap<>();
		
		if (id == null || id < 0) {
		
			result.setStatusCode("1");
			result.setStatusDescription("Can't Find Any Information of Any Profile :(");
			mapResult.put("Id", "Can't Be null or empty !");
			result.setResultMap(mapResult);
			return result;
		
		}
		
		result.setStatusCode("0");
		result.setStatusDescription("Now You Can See An Information Of This Profile :)");
		mapResult.put("Object", DocRepo.findById(id).orElse(new DoctorEntity()));
		result.setResultMap(mapResult);
		return result;
	
	}
	
	
	
	
	public ResultPojo CreateAppointmentForPatient(AppointmentEntity CreateAppointment) {
		
		ResultPojo result = new ResultPojo();
	
		Map<String, Object> mapResult = new HashMap<>();
		

		
		LocalTime start = LocalTime.of(7, 0);  // 8:00 AM
		LocalTime end = LocalTime.of(18, 0);   // 5:00 PM
		
		
		if (CreateAppointment.getTime().isAfter(start) && CreateAppointment.getTime().isBefore(end)) {

			appointmentRepo.save(CreateAppointment);
			result.setStatusCode("0");
			result.setStatusDescription("Success");
			mapResult.put("Success", "Appointment Created or Updated Successfuly");
			result.setResultMap(mapResult);
			return result;
		}
		
		else {
			
		result.setStatusCode("1");
		result.setStatusDescription("Faild");
		mapResult.put("Sorry !", "Appointment Can't Be Before 8 am & After 17 pm ");
		result.setResultMap(mapResult);
		return result;
			}

	}

	
	public ResultPojo deleteAppointmentForPatientById(Integer id) {
		
		
		
		ResultPojo result = new ResultPojo();
		
		Map<String, Object> mapResult = new HashMap<>();
		
		if (id == null || id < 0) {
		
			result.setStatusCode("1");
			result.setStatusDescription("Faild");
			mapResult.put("Sorry!", "An Appointment Can't Be Cancled :( Please Check Your Appointment Another Time");
			result.setResultMap(mapResult);
			return result;
		
		}
		
		else {
			
			appointmentRepo.deleteById(id);
			result.setStatusCode("0");
			result.setStatusDescription("Success");
			mapResult.put("Success", "An Appointment Was Deleted Successfuly :)");
			result.setResultMap(mapResult);
			return result;
			
			
		}
	}
	
	
	
	public ResultPojo getAvailableTimes (Integer doctorId) { 
		
		
		ResultPojo result = new ResultPojo();
		
		
		

		 List<AppointmentEntity> test = appointmentRepo.findTimesForDoctorOnCurrentDate(doctorId, LocalDate.now());
		 List<LocalTime> allAvailable = new ArrayList<>();
		 List<LocalTime> reservedTime = test.stream().map(x -> x.getTime()).collect(Collectors.toList());
		for(Integer i =8;i<=17;i++) {
			
			if(reservedTime.contains(LocalTime.of(i, 00))) {
				
				continue ;
				
			}
			else {
				allAvailable.add(LocalTime.of(i, 00));
				
				
				
			}
			
			
		}
		
		result.setResultMap(allAvailable);
		result.setStatusCode("0");
		result.setStatusDescription("Success");
		
		return result;
		
	}	 
	
	
	
	
	
	public ResultPojo getAllBookedTimes(Integer doctorId) {
		
		ResultPojo result = new ResultPojo();
		
		result.setResultMap(appointmentMapper.appointment(appointmentRepo.getAllBookedTimes(LocalDate.now(), doctorId)));
		result.setStatusCode("0");
		result.setStatusDescription("Success");
		

		return result ;
		
	}


	
	
	 public ResultPojo updateStatusById(Integer appointmentId , Integer status) {
			
			ResultPojo result = new ResultPojo();

		
	        appointmentRepo.updateStatusById(appointmentId, status);
	        
	        
	        result.setStatusCode("0");
	        result.setStatusDescription("Success");
	        result.setResultMap("Status Updated Successfuly :)");
	        return result ;
	    }
		
	
	 
	 

	  public ResultPojo countVisitsByDoctorIdAndPatientId( Integer doctorId, Integer patientId ) {
		  
			ResultPojo result = new ResultPojo();

	        int count = appointmentRepo.countVisitsByDoctorIdAndPatientId(doctorId, patientId);
	        
	        
	        
	        result.setStatusCode("0");
	        result.setStatusDescription("Success");
	        result.setResultMap("Count :" + count);
	        return result ;
	  }
	  

	 
	 
	 
	 
	 public File getSummary(LocalDate from, LocalDate to, Integer doctorId) throws IOException {
		 
		 
	        List<AppointmentEntity> appointments = appointmentRepo.getSummary(from, to, doctorId);
	        
	        
	        File file = new File("doctor_report.csv");
	        
	        FileWriter writer = new FileWriter(file);
	        
	        
	        writer.write("Appointment Id,Doctor Id,Patient Id,Status,Date,Time\n");
	        
	        for (AppointmentEntity appointment : appointments) {
	        
	        	writer.write(appointment.getAppointmentId() + "," + appointment.getDoctor_id() + "," + appointment.getPatient_id() + "," + appointment.getStatus() + "," + appointment.getDate() + "," + appointment.getTime() + "\n");
	        }
	        writer.flush();
	        writer.close();
	        return file;
	    }
	
	 
	}


	

	

