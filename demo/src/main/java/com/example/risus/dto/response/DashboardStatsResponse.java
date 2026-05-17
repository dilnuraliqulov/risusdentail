package com.example.risus.dto.response;


import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class DashboardStatsResponse {
    private long totalAppointments;
    private long pendingCount;
    private long approvedCount;
    private long rejectedCount;
    private Map<String, Long> appointmentsByService; // service title (en) → count
}