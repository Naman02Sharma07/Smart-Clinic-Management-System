package com.smartclinic.service;

import com.smartclinic.entity.Appointment;
import com.smartclinic.entity.Prescription;
import com.smartclinic.exception.ResourceNotFoundException;
import com.smartclinic.repository.AppointmentRepository;
import com.smartclinic.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository, AppointmentRepository appointmentRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public Prescription create(Long appointmentId, Prescription prescription) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found: " + appointmentId));
        prescription.setAppointment(appointment);
        return prescriptionRepository.save(prescription);
    }

    public List<Prescription> findAll() {
        return prescriptionRepository.findAll();
    }
}
