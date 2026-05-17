package com.example.risus.dto.request;


import com.example.risus.enums.AppointmentStatus;
import jakarta.validation.constraints.NotNull;

public record AppointmentStatusRequest(
        @NotNull AppointmentStatus status,
        String adminNote
) {}