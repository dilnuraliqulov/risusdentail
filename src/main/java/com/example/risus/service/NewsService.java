package com.example.risus.service;

import com.example.risus.dto.request.NewsRequest;
import com.example.risus.dto.response.NewsResponse;
import com.example.risus.entity.Admin;
import com.example.risus.entity.News;
import com.example.risus.exception.DuplicateSlugException;
import com.example.risus.exception.ResourceNotFoundException;
import com.example.risus.repository.AdminRepository;
import com.example.risus.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final AdminRepository adminRepository;

    // ── Public ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public Page<NewsResponse> getPublished(Pageable pageable) {
        return newsRepository.findByIsPublishedTrueOrderByPublishedAtDesc(pageable)
                .map(NewsResponse::from);
    }

    @Transactional(readOnly = true)
    public NewsResponse getBySlug(String slug) {
        return newsRepository.findBySlugAndIsPublishedTrue(slug)
                .map(NewsResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("News not found with slug: " + slug));
    }

    // ── Admin ─────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public Page<NewsResponse> getAll(Pageable pageable) {
        return newsRepository.findAll(pageable)
                .map(NewsResponse::from);
    }

    @Transactional
    public NewsResponse create(NewsRequest request, String adminEmail) {
        if (newsRepository.existsBySlug(request.slug())) {
            throw new DuplicateSlugException("News with slug '" + request.slug() + "' already exists");
        }

        Admin admin = adminRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        News news = News.builder()
                .slug(request.slug())
                .titleUz(request.titleUz())
                .titleRu(request.titleRu())
                .titleEn(request.titleEn())
                .bodyUz(request.bodyUz())
                .bodyRu(request.bodyRu())
                .bodyEn(request.bodyEn())
                .coverImageUrl(request.coverImageUrl())
                .isPublished(request.isPublished() != null ? request.isPublished() : false)
                .publishedAt(resolvePublishedAt(request))
                .createdBy(admin)
                .build();

        return NewsResponse.from(newsRepository.save(news));
    }

    @Transactional
    public NewsResponse update(UUID id, NewsRequest request) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News not found with id: " + id));

        if (!news.getSlug().equals(request.slug()) && newsRepository.existsBySlug(request.slug())) {
            throw new DuplicateSlugException("News with slug '" + request.slug() + "' already exists");
        }

        news.setSlug(request.slug());
        news.setTitleUz(request.titleUz());
        news.setTitleRu(request.titleRu());
        news.setTitleEn(request.titleEn());
        news.setBodyUz(request.bodyUz());
        news.setBodyRu(request.bodyRu());
        news.setBodyEn(request.bodyEn());
        news.setCoverImageUrl(request.coverImageUrl());
        if (request.isPublished() != null) news.setIsPublished(request.isPublished());
        news.setPublishedAt(resolvePublishedAt(request));

        return NewsResponse.from(newsRepository.save(news));
    }

    @Transactional
    public void delete(UUID id) {
        if (!newsRepository.existsById(id)) {
            throw new ResourceNotFoundException("News not found with id: " + id);
        }
        newsRepository.deleteById(id);
    }

    // If publishedAt not provided and article is being published, set it to now
    private LocalDateTime resolvePublishedAt(NewsRequest request) {
        if (request.publishedAt() != null) return request.publishedAt();
        if (Boolean.TRUE.equals(request.isPublished())) return LocalDateTime.now();
        return null;
    }
}
