package com.smartclinic.controller;

import com.smartclinic.dto.ApiResponse;
import com.smartclinic.dto.DoctorLoginRequestDto;
import com.smartclinic.dto.LoginResponseDto;
import com.smartclinic.entity.Doctor;
import com.smartclinic.service.DoctorService;
import com.smartclinic.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorService doctorService;
    private final TokenService tokenService;

    public DoctorController(DoctorService doctorService, TokenService tokenService) {
        this.doctorService = doctorService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@Valid @RequestBody DoctorLoginRequestDto request) {
        return ResponseEntity.ok(doctorService.validateDoctorCredentials(request));
    }

    @GetMapping
    public List<Doctor> getDoctors(@RequestParam(required = false) String speciality) {
        return speciality == null ? doctorService.getAllDoctors() : doctorService.getBySpeciality(speciality);
    }

    @GetMapping("/search")
    public List<Doctor> searchDoctors(@RequestParam String name) {
        return doctorService.searchByName(name);
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Long id) {
        return doctorService.getById(id);
    }

    @GetMapping("/{doctorId}/availability")
    public ResponseEntity<ApiResponse<List<String>>> getDoctorAvailability(
            @PathVariable Long doctorId,
            @RequestParam LocalDate date,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        tokenService.extractSubject(token);

        List<String> slots = doctorService.getAvailableTimeSlots(doctorId, date);
        return ResponseEntity.ok(ApiResponse.ok("Doctor availability fetched", slots));
    }
}
