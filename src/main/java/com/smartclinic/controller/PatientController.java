package com.smartclinic.controller;

import com.smartclinic.dto.ApiResponse;
import com.smartclinic.dto.LoginResponseDto;
import com.smartclinic.dto.PatientLoginRequestDto;
import com.smartclinic.entity.Patient;
import com.smartclinic.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@Valid @RequestBody PatientLoginRequestDto request) {
        return ResponseEntity.ok(patientService.validatePatientCredentials(request));
    }

    @PostMapping
    public Patient create(@Valid @RequestBody Patient patient) {
        return patientService.createPatient(patient);
    }

    @GetMapping
    public List<Patient> getAll() {
        return patientService.getAllPatients();
    }
}
