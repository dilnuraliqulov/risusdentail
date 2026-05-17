package com.example.risus.service;

import com.example.risus.dto.request.ServiceCategoryRequest;
import com.example.risus.dto.response.ServiceCategoryResponse;
import com.example.risus.entity.ServiceCategory;
import com.example.risus.exception.DuplicateSlugException;
import com.example.risus.exception.ResourceNotFoundException;
import com.example.risus.repository.ServiceCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceCategoryService {

    private final ServiceCategoryRepository categoryRepository;

    // ── Public ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<ServiceCategoryResponse> getAllActiveWithServices() {
        return categoryRepository.findByIsActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(ServiceCategoryResponse::from)
                .toList();
    }

    // ── Admin ─────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<ServiceCategoryResponse> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(ServiceCategoryResponse::from)
                .toList();
    }

    @Transactional
    public ServiceCategoryResponse create(ServiceCategoryRequest request) {
        if (categoryRepository.existsBySlug(request.slug())) {
            throw new DuplicateSlugException("Category with slug '" + request.slug() + "' already exists");
        }

        ServiceCategory category = ServiceCategory.builder()
                .slug(request.slug())
                .nameUz(request.nameUz())
                .nameRu(request.nameRu())
                .nameEn(request.nameEn())
                .displayOrder(request.displayOrder())
                .isActive(request.isActive() != null ? request.isActive() : true)
                .build();

        return ServiceCategoryResponse.from(categoryRepository.save(category));
    }

    @Transactional
    public ServiceCategoryResponse update(UUID id, ServiceCategoryRequest request) {
        ServiceCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        // Allow same slug on update (only block if another record has it)
        if (!category.getSlug().equals(request.slug()) && categoryRepository.existsBySlug(request.slug())) {
            throw new DuplicateSlugException("Category with slug '" + request.slug() + "' already exists");
        }

        category.setSlug(request.slug());
        category.setNameUz(request.nameUz());
        category.setNameRu(request.nameRu());
        category.setNameEn(request.nameEn());
        category.setDisplayOrder(request.displayOrder());
        if (request.isActive() != null) category.setIsActive(request.isActive());

        return ServiceCategoryResponse.from(categoryRepository.save(category));
    }

    @Transactional
    public void delete(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}