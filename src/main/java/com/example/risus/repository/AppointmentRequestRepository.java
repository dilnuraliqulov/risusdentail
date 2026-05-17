package com.example.risus.repository;


import com.example.risus.entity.AppointmentRequest;
import com.example.risus.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface AppointmentRequestRepository extends JpaRepository<AppointmentRequest, UUID> {

    Page<AppointmentRequest> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<AppointmentRequest> findByStatusOrderByCreatedAtDesc(
            AppointmentStatus status, Pageable pageable);

    long countByStatus(AppointmentStatus status);

    // For dashboard stats — count appointments grouped by service
    @Query("SELECT a.service.id, COUNT(a) FROM AppointmentRequest a GROUP BY a.service.id")
    List<Object[]> countGroupedByService();
}
