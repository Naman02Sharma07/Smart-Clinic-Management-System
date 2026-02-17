package com.smartclinic.controller;

import com.smartclinic.entity.Doctor;
import com.smartclinic.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final DoctorService doctorService;

    public AdminController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/doctors")
    public Doctor addDoctor(@Valid @RequestBody Doctor doctor) {
        return doctorService.createDoctor(doctor);
    }
}
