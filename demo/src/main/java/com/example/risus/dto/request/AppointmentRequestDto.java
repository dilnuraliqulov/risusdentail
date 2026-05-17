package com.example.risus.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record AppointmentRequestDto(

        @NotBlank(message = "Full name is required")
        String fullName,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^[+]?[0-9]{9,15}$", message = "Invalid phone number")
        String phoneNumber,

        @NotNull(message = "Service must be selected")
        UUID serviceId,

        String additionalInfo
) {}
