package com.example.FinalProject.DentistRepository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.FinalProject.DentistEntity.AppointmentEntity;

@Repository
public interface AppointmentRepo extends JpaRepository<AppointmentEntity, Integer> {

	@Query(nativeQuery = true,value = "select * from Doctor_Patient e where doctor_id =:id and date =:date")
	List<AppointmentEntity> findTimesForDoctorOnCurrentDate(Integer id,LocalDate date);	
	
	
	
	@Query(nativeQuery = true,value = "select * from Doctor_Patient where doctor_id=:doctorId and date=:date")
	List<AppointmentEntity> getAllBookedTimes(LocalDate date , Integer doctorId);

	
	
    @Transactional
	@Modifying
	@Query(nativeQuery = true , value = "UPDATE Doctor_Patient SET status = :status WHERE id = :appointmentId")
    public void updateStatusById(@Param("appointmentId") Integer appointmentId, @Param("status") Integer status);
	
    
    
    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM Doctor_Patient WHERE doctor_id = :doctorId AND patient_id = :patientId AND status = 0")
    public int countVisitsByDoctorIdAndPatientId(@Param("doctorId") Integer doctorId, @Param("patientId") Integer patientId);


    @Query(nativeQuery = true ,value = "SELECT * FROM Doctor_Patient WHERE doctor_id =:doctor_id AND date BETWEEN :fromDate AND :toDate")
    List<AppointmentEntity> getSummary(@Param("fromDate") LocalDate from, @Param("toDate") LocalDate to, @Param("doctor_id") Integer doctorId);


    @Query(nativeQuery = true,value = "select * from Doctor_Patient where patient_id=:patient_id  AND date BETWEEN :fromDate AND :toDate")
	List<AppointmentEntity> CheckAllTimeLiens(@Param("fromDate") LocalDate from, @Param("toDate") LocalDate to, @Param("patient_id") Integer doctorId);



}



