package com.example.risus.controller.admin;

import com.example.risus.dto.request.ServiceCategoryRequest;
import com.example.risus.dto.request.ServiceRequest;
import com.example.risus.dto.response.ServiceCategoryResponse;
import com.example.risus.dto.response.ServiceResponse;
import com.example.risus.service.ClinicalService;
import com.example.risus.service.ServiceCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/services")
@RequiredArgsConstructor
public class AdminServiceController {

    private final ClinicalService clinicalService;
    private final ServiceCategoryService categoryService;

    // ── Services ──────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<ServiceResponse>> getAll() {
        return ResponseEntity.ok(clinicalService.getAll());
    }

    @PostMapping
    public ResponseEntity<ServiceResponse> create(@Valid @RequestBody ServiceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clinicalService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> update(@PathVariable UUID id,
                                                  @Valid @RequestBody ServiceRequest request) {
        return ResponseEntity.ok(clinicalService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        clinicalService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ── Categories ────────────────────────────────────────────

    @GetMapping("/categories")
    public ResponseEntity<List<ServiceCategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @PostMapping("/categories")
    public ResponseEntity<ServiceCategoryResponse> createCategory(
            @Valid @RequestBody ServiceCategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(request));
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<ServiceCategoryResponse> updateCategory(
            @PathVariable UUID id,
            @Valid @RequestBody ServiceCategoryRequest request) {
        return ResponseEntity.ok(categoryService.update(id, request));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}