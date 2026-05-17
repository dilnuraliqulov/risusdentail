package com.example.risus.repository;


import com.example.risus.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Service, UUID> {
    List<Service> findByIsActiveTrueOrderByDisplayOrderAsc();

    List<Service> findByCategoryIdAndIsActiveTrueOrderByDisplayOrderAsc(UUID categoryId);

    Optional<Service> findBySlug(String slug);

    boolean existsBySlug(String slug);


}
