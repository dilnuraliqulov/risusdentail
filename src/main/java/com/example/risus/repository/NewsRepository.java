package com.example.risus.repository;


import com.example.risus.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface NewsRepository extends JpaRepository<News, UUID> {
    Page<News> findByIsPublishedTrueOrderByPublishedAtDesc(Pageable pageable);
    Optional<News> findBySlugAndIsPublishedTrue(String slug);
    Optional<News> findBySlug(String slug);
    boolean existsBySlug(String slug);
}