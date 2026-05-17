package com.example.risus.repository;


import com.example.risus.entity.PriceItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PriceItemRepository extends JpaRepository<PriceItem, UUID> {
    List<PriceItem> findByServiceIdOrderByDisplayOrderAsc(UUID serviceId);
    void deleteByServiceId(UUID serviceId);
}