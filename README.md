# Smart Clinic Management System

Spring Boot backend implementing REST APIs for Admin, Doctor, Patient, Appointment, Prescription and JWT authentication.

## Run
```bash
mvn spring-boot:run
```

## Key endpoints
- `POST /api/doctors/login`
- `GET /api/doctors/{doctorId}/availability?date=2030-01-15`
- `POST /api/appointments`
- `GET /api/appointments/doctor/{doctorId}/date?date=2030-01-15`
- `POST /api/prescriptions/appointment/{appointmentId}`

## Default seeded users
- Admin: `admin / admin123`
- Doctor: `alice@clinic.com / doc123`
- Patient: `john@patient.com / pat123`
