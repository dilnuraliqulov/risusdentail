package com.example.risus.dto.response;

import com.example.risus.entity.AppointmentRequest;
import com.example.risus.enums.AppointmentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AppointmentResponse {

    private UUID id;
    private String fullName;
    private String phoneNumber;
    private UUID serviceId;
    private String serviceTitle; // en title for admin readability
    private String additionalInfo;
    private AppointmentStatus status;
    private String adminNote;
    private LocalDateTime createdAt;

    public static AppointmentResponse from(AppointmentRequest a) {
        return AppointmentResponse.builder()
                .id(a.getId())
                .fullName(a.getFullName())
                .phoneNumber(a.getPhoneNumber())
                .serviceId(a.getService().getId())
                .serviceTitle(a.getService().getTitleEn())
                .additionalInfo(a.getAdditionalInfo())
                .status(a.getStatus())
                .adminNote(a.getAdminNote())
                .createdAt(a.getCreatedAt())
                .build();
    }
}