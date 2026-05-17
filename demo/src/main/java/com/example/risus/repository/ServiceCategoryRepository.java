package com.example.risus.repository;


import com.example.risus.entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, UUID> {
    List<ServiceCategory> findByIsActiveTrueOrderByDisplayOrderAsc();
    Optional<ServiceCategory> findBySlug(String slug);
    boolean existsBySlug(String slug);
}
