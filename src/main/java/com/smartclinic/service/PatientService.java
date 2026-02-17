package com.smartclinic.service;

import com.smartclinic.dto.ApiResponse;
import com.smartclinic.dto.LoginResponseDto;
import com.smartclinic.dto.PatientLoginRequestDto;
import com.smartclinic.entity.Patient;
import com.smartclinic.exception.ResourceNotFoundException;
import com.smartclinic.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final TokenService tokenService;

    public PatientService(PatientRepository patientRepository, TokenService tokenService) {
        this.patientRepository = patientRepository;
        this.tokenService = tokenService;
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + id));
    }

    public ApiResponse<LoginResponseDto> validatePatientCredentials(PatientLoginRequestDto request) {
        Patient patient = patientRepository.findByEmailOrPhoneNumber(request.getEmailOrPhone(), request.getEmailOrPhone())
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient credentials"));

        if (!patient.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Invalid patient credentials");
        }

        return ApiResponse.ok("Patient login successful", new LoginResponseDto(tokenService.generateToken(patient.getEmail())));
    }
}
