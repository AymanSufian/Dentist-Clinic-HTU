create database Dentist ;

create table Doctor (
	id int primary key auto_increment ,
	name varchar(255) ,
	username varchar(255) ,
	password varchar(255),
	national_id int , 
	phone_number varchar(255),
	Doctor_speciality varchar(255)
					);

create table Patient (
	id int primary key auto_increment ,
	Patient_name varchar(255) ,
	username varchar(255) ,
	password varchar(255),
	phone_number varchar(255),
	age int ,
	gender varchar(255)
					);

create table Doctor_Patient (
	id int primary key auto_increment ,
	doctor_id int  ,
	patient_id int ,
	status int ,
	date date,
	time time,

	unique(patient_id,date,time),
	unique(doctor_id,date,time),
	unique(patient_id,doctor_id,date),

	foreign key (doctor_id) references Doctor(id),
	foreign key (patient_id) references Patient(id)
							);

select * from Doctor ;
select * from Patient ;
select * from Doctor_Patient ;

insert into patient(Patient_name,username,password,phone_number,age,gender) values ('ayman','ayman','ayman123','1231231232132123',25,'male');
insert into doctor_patient (doctor_id,patient_id,status,date,time) values(1,1,0,'2023-01-14','08:00');

