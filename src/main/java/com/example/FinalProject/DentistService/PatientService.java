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

import com.example.FinalProject.DentistEntity.AppointmentEntity;
import com.example.FinalProject.DentistEntity.DoctorEntity;
import com.example.FinalProject.DentistEntity.PatientEntity;
import com.example.FinalProject.DentistRepository.AppointmentRepo;
import com.example.FinalProject.DentistRepository.DoctorRepo;
import com.example.FinalProject.DentistRepository.PatientRepo;
import com.example.FinalProject.POJO.PatientLogin;
import com.example.FinalProject.POJO.ResultPojo;
import com.example.FinalProject.TokenUtility.PatientToken;

@Service
public class PatientService {

	@Autowired
	private PatientRepo patientRepo ;
	
	@Autowired
	private DoctorRepo doctorRepo ;
	
	
	@Autowired
	private AppointmentRepo appointmentRepo ;
	
	
	@Autowired
	private PatientToken patientToken ;
	
	
	public ResultPojo Patientlogin(PatientLogin login) {
		
		ResultPojo result = new ResultPojo();
		
		Map<String, Object> mapResult = new HashMap<>();
		
		PatientEntity user = patientRepo.findByUsername(login.getUsername());
		
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
		
		String token = patientToken.generateToken(login.getUsername());
		mapResult.put("token", token);
		result.setStatusCode("1");
		result.setStatusDescription("Success");
		result.setResultMap(mapResult);
		return result;
	
	}
	
	
	
	
	
	
	
	public ResultPojo RegisterPatient(PatientEntity AddPatient) {
		
		ResultPojo result = new ResultPojo();
	
		Map<String, Object> mapResult = new HashMap<>();
		

		
		
		patientRepo.save(AddPatient);
		result.setStatusCode("0");
		result.setStatusDescription("Success");
		mapResult.put("Success", "Patient Added or Updated");
		result.setResultMap(mapResult);
		return result;

	}
	
	
	public List<DoctorEntity> findallDoctorss() {
		
		
		return doctorRepo.findAll();
	}
	
	
	
	
	public ResultPojo CreateOrUpdateAnAppointment(AppointmentEntity CreateAppointment) {
		
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
	
	
	
	public ResultPojo deleteAppointmentById(Integer id) {
		
		
			
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
		
		
		
		public List<LocalTime> getAvailableTimesForDoctors (Integer doctorId) { 
			

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
			return allAvailable;
			
		}	
		
		

	
		public List<AppointmentEntity> getAllBookedTimes (Integer doctorId) { 
			

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
			return test;
			
		}	 
		 	
		
		 public File CheckAllTimeLiens(LocalDate from, LocalDate to, Integer patient_id) throws IOException {
			 
			 
		        List<AppointmentEntity> appointments = appointmentRepo.getSummary(from, to, patient_id);
		        
		        
		        File file = new File("CheckTimelines.csv");
		        
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
