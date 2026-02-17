INSERT INTO admins (id, username, password) VALUES (1, 'admin', 'admin123');

INSERT INTO doctors (id, name, speciality, email, password) VALUES
(1, 'Dr. Alice Carter', 'Cardiology', 'alice@clinic.com', 'doc123'),
(2, 'Dr. Brian Lee', 'Dermatology', 'brian@clinic.com', 'doc123');

INSERT INTO doctor_available_times (doctor_id, available_time) VALUES
(1, '09:00'), (1, '10:00'), (1, '11:00'),
(2, '14:00'), (2, '15:00');

INSERT INTO patients (id, name, email, phone_number, password) VALUES
(1, 'John Doe', 'john@patient.com', '1111111111', 'pat123'),
(2, 'Jane Smith', 'jane@patient.com', '2222222222', 'pat123'),
(3, 'Mark Kim', 'mark@patient.com', '3333333333', 'pat123'),
(4, 'Rita Patel', 'rita@patient.com', '4444444444', 'pat123'),
(5, 'Sam Wong', 'sam@patient.com', '5555555555', 'pat123');

INSERT INTO appointments (id, doctor_id, patient_id, appointment_time, status) VALUES
(1, 1, 1, TIMESTAMP '2030-01-15 09:00:00', 'BOOKED'),
(2, 1, 2, TIMESTAMP '2030-01-15 10:00:00', 'BOOKED'),
(3, 2, 3, TIMESTAMP '2030-01-16 14:00:00', 'BOOKED');
