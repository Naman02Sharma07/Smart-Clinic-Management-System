# Smart Clinic Management System Schema Design (MySQL)

## Tables
```sql
CREATE TABLE admins (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL
);

CREATE TABLE doctors (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(150) NOT NULL,
  speciality VARCHAR(100) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL
);

CREATE TABLE patients (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(150) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  phone_number VARCHAR(20) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL
);

CREATE TABLE appointments (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  doctor_id BIGINT NOT NULL,
  patient_id BIGINT NOT NULL,
  appointment_time DATETIME NOT NULL,
  status VARCHAR(50) NOT NULL,
  CONSTRAINT fk_appointment_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
  CONSTRAINT fk_appointment_patient FOREIGN KEY (patient_id) REFERENCES patients(id)
);

CREATE TABLE prescriptions (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  appointment_id BIGINT NOT NULL,
  medication VARCHAR(255) NOT NULL,
  notes TEXT NOT NULL,
  CONSTRAINT fk_prescription_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);
```

## Stored procedures
```sql
DELIMITER $$
CREATE PROCEDURE GetDailyAppointmentReportByDoctor(IN p_doctor_id BIGINT, IN p_date DATE)
BEGIN
  SELECT a.id, a.appointment_time, a.status, p.name AS patient_name
  FROM appointments a
  JOIN patients p ON p.id = a.patient_id
  WHERE a.doctor_id = p_doctor_id
    AND DATE(a.appointment_time) = p_date
  ORDER BY a.appointment_time;
END$$

CREATE PROCEDURE GetDoctorWithMostPatientsByMonth(IN p_month INT, IN p_year INT)
BEGIN
  SELECT d.id, d.name, COUNT(DISTINCT a.patient_id) AS patient_count
  FROM appointments a
  JOIN doctors d ON d.id = a.doctor_id
  WHERE MONTH(a.appointment_time) = p_month
    AND YEAR(a.appointment_time) = p_year
  GROUP BY d.id, d.name
  ORDER BY patient_count DESC
  LIMIT 1;
END$$

CREATE PROCEDURE GetDoctorWithMostPatientsByYear(IN p_year INT)
BEGIN
  SELECT d.id, d.name, COUNT(DISTINCT a.patient_id) AS patient_count
  FROM appointments a
  JOIN doctors d ON d.id = a.doctor_id
  WHERE YEAR(a.appointment_time) = p_year
  GROUP BY d.id, d.name
  ORDER BY patient_count DESC
  LIMIT 1;
END$$
DELIMITER ;
```
