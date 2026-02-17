# Capstone Final Submission Package

> Replace placeholders with your real public GitHub URLs after pushing this branch.

## Public links
1. Issues (Doctor, Patient, Admin user stories): `https://github.com/<username>/<repo>/issues`
2. schema-design.md: `https://github.com/<username>/<repo>/blob/main/schema-design.md`
3. Doctor.java: `https://github.com/<username>/<repo>/blob/main/src/main/java/com/smartclinic/entity/Doctor.java`
4. Appointment.java: `https://github.com/<username>/<repo>/blob/main/src/main/java/com/smartclinic/entity/Appointment.java`
5. DoctorController.java: `https://github.com/<username>/<repo>/blob/main/src/main/java/com/smartclinic/controller/DoctorController.java`
6. AppointmentService.java: `https://github.com/<username>/<repo>/blob/main/src/main/java/com/smartclinic/service/AppointmentService.java`
7. PrescriptionController.java: `https://github.com/<username>/<repo>/blob/main/src/main/java/com/smartclinic/controller/PrescriptionController.java`
8. PatientRepository.java: `https://github.com/<username>/<repo>/blob/main/src/main/java/com/smartclinic/repository/PatientRepository.java`
9. TokenService.java: `https://github.com/<username>/<repo>/blob/main/src/main/java/com/smartclinic/service/TokenService.java`
10. DoctorService.java: `https://github.com/<username>/<repo>/blob/main/src/main/java/com/smartclinic/service/DoctorService.java`
11. Dockerfile: `https://github.com/<username>/<repo>/blob/main/Dockerfile`
12. GitHub Actions workflow: `https://github.com/<username>/<repo>/blob/main/.github/workflows/backend-ci.yml`

## Screenshot checklist
- Admin portal login screen
- Doctor portal login screen
- Patient portal login screen
- Admin adds a doctor
- Patient searches doctor by name
- Doctor views all appointments

## SQL evidence to submit
```sql
SHOW TABLES;
SELECT * FROM patients LIMIT 5;
CALL GetDailyAppointmentReportByDoctor(1, '2030-01-15');
CALL GetDoctorWithMostPatientsByMonth(1, 2030);
CALL GetDoctorWithMostPatientsByYear(2030);
```

## curl evidence to submit
```bash
# login as doctor
curl -X POST http://localhost:8080/api/doctors/login \
  -H "Content-Type: application/json" \
  -d '{"email":"alice@clinic.com","password":"doc123"}'

# all doctors
curl -H "Authorization: Bearer <token>" http://localhost:8080/api/doctors

# appointments by patient
curl -H "Authorization: Bearer <token>" http://localhost:8080/api/appointments/patient/1

# doctors by speciality
curl -H "Authorization: Bearer <token>" "http://localhost:8080/api/doctors?speciality=Cardiology"
```
