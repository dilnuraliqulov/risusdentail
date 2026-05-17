package com.example.risus.service;

import com.example.risus.dto.request.ServiceRequest;
import com.example.risus.dto.response.ServiceResponse;
import com.example.risus.entity.Service;
import com.example.risus.entity.ServiceCategory;
import com.example.risus.exception.DuplicateSlugException;
import com.example.risus.exception.ResourceNotFoundException;
import com.example.risus.repository.ServiceCategoryRepository;
import com.example.risus.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ClinicalService {

    private final ServiceRepository serviceRepository;
    private final ServiceCategoryRepository categoryRepository;

    // ── Public ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<ServiceResponse> getAllActive() {
        return serviceRepository.findByIsActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(ServiceResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ServiceResponse getBySlug(String slug) {
        return serviceRepository.findBySlug(slug)
                .filter(Service::getIsActive)
                .map(ServiceResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with slug: " + slug));
    }

    @Transactional(readOnly = true)
    public List<ServiceResponse> getByCategory(UUID categoryId) {
        return serviceRepository
                .findByCategoryIdAndIsActiveTrueOrderByDisplayOrderAsc(categoryId)
                .stream()
                .map(ServiceResponse::from)
                .toList();
    }

    // ── Admin ─────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<ServiceResponse> getAll() {
        return serviceRepository.findAll()
                .stream()
                .map(ServiceResponse::from)
                .toList();
    }

    @Transactional
    public ServiceResponse create(ServiceRequest request) {
        if (serviceRepository.existsBySlug(request.slug())) {
            throw new DuplicateSlugException("Service with slug '" + request.slug() + "' already exists");
        }

        ServiceCategory category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.categoryId()));

        Service service = Service.builder()
                .category(category)
                .slug(request.slug())
                .titleUz(request.titleUz())
                .titleRu(request.titleRu())
                .titleEn(request.titleEn())
                .descriptionUz(request.descriptionUz())
                .descriptionRu(request.descriptionRu())
                .descriptionEn(request.descriptionEn())
                .heroImageUrl(request.heroImageUrl())
                .displayOrder(request.displayOrder())
                .isActive(request.isActive() != null ? request.isActive() : true)
                .build();

        return ServiceResponse.from(serviceRepository.save(service));
    }

    @Transactional
    public ServiceResponse update(UUID id, ServiceRequest request) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));

        if (!service.getSlug().equals(request.slug()) && serviceRepository.existsBySlug(request.slug())) {
            throw new DuplicateSlugException("Service with slug '" + request.slug() + "' already exists");
        }

        ServiceCategory category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.categoryId()));

        service.setCategory(category);
        service.setSlug(request.slug());
        service.setTitleUz(request.titleUz());
        service.setTitleRu(request.titleRu());
        service.setTitleEn(request.titleEn());
        service.setDescriptionUz(request.descriptionUz());
        service.setDescriptionRu(request.descriptionRu());
        service.setDescriptionEn(request.descriptionEn());
        service.setHeroImageUrl(request.heroImageUrl());
        service.setDisplayOrder(request.displayOrder());
        if (request.isActive() != null) service.setIsActive(request.isActive());

        return ServiceResponse.from(serviceRepository.save(service));
    }

    @Transactional
    public void delete(UUID id) {
        if (!serviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Service not found with id: " + id);
        }
        serviceRepository.deleteById(id);
    }
}
