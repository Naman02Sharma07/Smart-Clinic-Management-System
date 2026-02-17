package com.smartclinic.service;

import com.smartclinic.dto.AppointmentRequestDto;
import com.smartclinic.dto.AppointmentResponseDto;
import com.smartclinic.entity.Appointment;
import com.smartclinic.entity.Doctor;
import com.smartclinic.entity.Patient;
import com.smartclinic.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;

    public AppointmentService(AppointmentRepository appointmentRepository, DoctorService doctorService, PatientService patientService) {
        this.appointmentRepository = appointmentRepository;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    public AppointmentResponseDto scheduleAppointment(AppointmentRequestDto dto) {
        Doctor doctor = doctorService.getById(dto.getDoctorId());
        Patient patient = patientService.getById(dto.getPatientId());

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setStatus("BOOKED");

        return toDto(appointmentRepository.save(appointment));
    }

    public List<AppointmentResponseDto> getByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId).stream().map(this::toDto).toList();
    }

    public List<AppointmentResponseDto> getByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId).stream().map(this::toDto).toList();
    }

    public List<AppointmentResponseDto> getByDoctorAndDate(Long doctorId, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay().minusNanos(1);
        return appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(doctorId, start, end)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private AppointmentResponseDto toDto(Appointment appointment) {
        AppointmentResponseDto dto = new AppointmentResponseDto();
        dto.setAppointmentId(appointment.getId());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setDoctorName(appointment.getDoctor().getName());
        dto.setPatientId(appointment.getPatient().getId());
        dto.setPatientName(appointment.getPatient().getName());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setStatus(appointment.getStatus());
        return dto;
    }
}
