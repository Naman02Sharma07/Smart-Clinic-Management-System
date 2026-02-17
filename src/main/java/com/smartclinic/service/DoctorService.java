package com.smartclinic.service;

import com.smartclinic.dto.ApiResponse;
import com.smartclinic.dto.DoctorLoginRequestDto;
import com.smartclinic.dto.LoginResponseDto;
import com.smartclinic.entity.Doctor;
import com.smartclinic.exception.ResourceNotFoundException;
import com.smartclinic.repository.AppointmentRepository;
import com.smartclinic.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final TokenService tokenService;

    public DoctorService(DoctorRepository doctorRepository, AppointmentRepository appointmentRepository, TokenService tokenService) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.tokenService = tokenService;
    }

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public List<Doctor> searchByName(String name) {
        return doctorRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Doctor> getBySpeciality(String speciality) {
        return doctorRepository.findBySpecialityIgnoreCase(speciality);
    }

    public Doctor getById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found: " + id));
    }

    public List<String> getAvailableTimeSlots(Long doctorId, LocalDate date) {
        Doctor doctor = getById(doctorId);
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay().minusNanos(1);
        List<LocalDateTime> booked = appointmentRepository
                .findByDoctorIdAndAppointmentTimeBetween(doctorId, start, end)
                .stream()
                .map(a -> a.getAppointmentTime())
                .toList();

        return doctor.getAvailableTimes().stream()
                .filter(slot -> booked.stream().noneMatch(b -> b.toLocalTime().toString().startsWith(slot)))
                .toList();
    }

    public ApiResponse<LoginResponseDto> validateDoctorCredentials(DoctorLoginRequestDto request) {
        Doctor doctor = doctorRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor credentials"));

        if (!doctor.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Invalid doctor credentials");
        }

        String token = tokenService.generateToken(doctor.getEmail());
        return ApiResponse.ok("Doctor login successful", new LoginResponseDto(token));
    }
}
