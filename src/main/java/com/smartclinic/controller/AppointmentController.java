package com.smartclinic.controller;

import com.smartclinic.dto.AppointmentRequestDto;
import com.smartclinic.dto.AppointmentResponseDto;
import com.smartclinic.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public AppointmentResponseDto schedule(@Valid @RequestBody AppointmentRequestDto dto) {
        return appointmentService.scheduleAppointment(dto);
    }

    @GetMapping("/patient/{patientId}")
    public List<AppointmentResponseDto> getByPatient(@PathVariable Long patientId) {
        return appointmentService.getByPatient(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<AppointmentResponseDto> getByDoctor(@PathVariable Long doctorId) {
        return appointmentService.getByDoctor(doctorId);
    }

    @GetMapping("/doctor/{doctorId}/date")
    public List<AppointmentResponseDto> getByDoctorAndDate(@PathVariable Long doctorId, @RequestParam LocalDate date) {
        return appointmentService.getByDoctorAndDate(doctorId, date);
    }
}
