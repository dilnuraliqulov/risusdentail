package com.example.risus.controller.public_api;

import com.example.risus.dto.response.ServiceCategoryResponse;
import com.example.risus.dto.response.ServiceResponse;
import com.example.risus.service.ClinicalService;
import com.example.risus.service.ServiceCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/services")
@RequiredArgsConstructor
public class PublicServiceController {

    private final ClinicalService clinicalService;
    private final ServiceCategoryService categoryService;

    // All active services flat list
    @GetMapping
    public ResponseEntity<List<ServiceResponse>> getAll() {
        return ResponseEntity.ok(clinicalService.getAllActive());
    }

    // Single service by slug — used for individual service page
    @GetMapping("/{slug}")
    public ResponseEntity<ServiceResponse> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(clinicalService.getBySlug(slug));
    }

    // All categories with their services nested — used for services overview page
    @GetMapping("/categories")
    public ResponseEntity<List<ServiceCategoryResponse>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllActiveWithServices());
    }

    // Services filtered by category
    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<ServiceResponse>> getByCategory(@PathVariable UUID categoryId) {
        return ResponseEntity.ok(clinicalService.getByCategory(categoryId));
    }
}