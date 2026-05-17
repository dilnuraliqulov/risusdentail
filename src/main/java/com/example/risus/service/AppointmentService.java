package com.example.risus.service;

import com.example.risus.dto.request.AppointmentRequestDto;
import com.example.risus.dto.request.AppointmentStatusRequest;
import com.example.risus.dto.response.AppointmentResponse;
import com.example.risus.dto.response.DashboardStatsResponse;
import com.example.risus.entity.AppointmentRequest;
import com.example.risus.entity.Service;
import com.example.risus.enums.AppointmentStatus;
import com.example.risus.exception.ResourceNotFoundException;
import com.example.risus.repository.AppointmentRequestRepository;
import com.example.risus.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRequestRepository appointmentRepository;
    private final ServiceRepository serviceRepository;

    // ── Public ────────────────────────────────────────────────

    @Transactional
    public AppointmentResponse submit(AppointmentRequestDto request) {
        Service service = serviceRepository.findById(request.serviceId())
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + request.serviceId()));

        AppointmentRequest appointment = AppointmentRequest.builder()
                .fullName(request.fullName())
                .phoneNumber(request.phoneNumber())
                .service(service)
                .additionalInfo(request.additionalInfo())
                .status(AppointmentStatus.PENDING)
                .build();

        return AppointmentResponse.from(appointmentRepository.save(appointment));
    }

    // ── Admin ─────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public Page<AppointmentResponse> getAll(Pageable pageable) {
        return appointmentRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(AppointmentResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<AppointmentResponse> getByStatus(AppointmentStatus status, Pageable pageable) {
        return appointmentRepository.findByStatusOrderByCreatedAtDesc(status, pageable)
                .map(AppointmentResponse::from);
    }

    @Transactional(readOnly = true)
    public AppointmentResponse getById(UUID id) {
        return appointmentRepository.findById(id)
                .map(AppointmentResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
    }

    @Transactional
    public AppointmentResponse updateStatus(UUID id, AppointmentStatusRequest request) {
        AppointmentRequest appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        appointment.setStatus(request.status());
        if (request.adminNote() != null) appointment.setAdminNote(request.adminNote());

        return AppointmentResponse.from(appointmentRepository.save(appointment));
    }

    @Transactional(readOnly = true)
    public DashboardStatsResponse getDashboardStats() {
        long total = appointmentRepository.count();
        long pending = appointmentRepository.countByStatus(AppointmentStatus.PENDING);
        long approved = appointmentRepository.countByStatus(AppointmentStatus.APPROVED);
        long rejected = appointmentRepository.countByStatus(AppointmentStatus.REJECTED);

        // Build service breakdown map
        List<Object[]> rows = appointmentRepository.countGroupedByService();
        Map<String, Long> byService = new HashMap<>();

        for (Object[] row : rows) {
            UUID serviceId = (UUID) row[0];
            Long count = (Long) row[1];
            serviceRepository.findById(serviceId).ifPresent(service ->
                    byService.put(service.getTitleEn(), count)
            );
        }

        return DashboardStatsResponse.builder()
                .totalAppointments(total)
                .pendingCount(pending)
                .approvedCount(approved)
                .rejectedCount(rejected)
                .appointmentsByService(byService)
                .build();
    }
}