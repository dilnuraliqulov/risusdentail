package com.example.risus.controller.admin;

import com.example.risus.dto.request.AppointmentStatusRequest;
import com.example.risus.dto.response.AppointmentResponse;
import com.example.risus.dto.response.DashboardStatsResponse;
import com.example.risus.enums.AppointmentStatus;
import com.example.risus.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/appointments")
@RequiredArgsConstructor
public class AdminAppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<Page<AppointmentResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) AppointmentStatus status) {

        Pageable pageable = PageRequest.of(page, size);

        if (status != null) {
            return ResponseEntity.ok(appointmentService.getByStatus(status, pageable));
        }
        return ResponseEntity.ok(appointmentService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.getById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AppointmentResponse> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody AppointmentStatusRequest request) {
        return ResponseEntity.ok(appointmentService.updateStatus(id, request));
    }

    @GetMapping("/dashboard/stats")
    public ResponseEntity<DashboardStatsResponse> getStats() {
        return ResponseEntity.ok(appointmentService.getDashboardStats());
    }
}