package com.smartclinic.controller;

import com.smartclinic.dto.ApiResponse;
import com.smartclinic.entity.Prescription;
import com.smartclinic.service.PrescriptionService;
import com.smartclinic.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;
    private final TokenService tokenService;

    public PrescriptionController(PrescriptionService prescriptionService, TokenService tokenService) {
        this.prescriptionService = prescriptionService;
        this.tokenService = tokenService;
    }

    @PostMapping("/appointment/{appointmentId}")
    public ResponseEntity<ApiResponse<Prescription>> create(
            @PathVariable Long appointmentId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @Valid @RequestBody Prescription prescription) {

        String token = authHeader.replace("Bearer ", "");
        tokenService.extractSubject(token);

        Prescription created = prescriptionService.create(appointmentId, prescription);
        return ResponseEntity.ok(ApiResponse.ok("Prescription created", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Prescription>>> all() {
        return ResponseEntity.ok(ApiResponse.ok("Prescriptions fetched", prescriptionService.findAll()));
    }
}
