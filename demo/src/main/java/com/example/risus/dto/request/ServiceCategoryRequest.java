package com.example.risus.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServiceCategoryRequest(
        @NotBlank String slug,
        @NotBlank String nameUz,
        @NotBlank String nameRu,
        @NotBlank String nameEn,
        @NotNull Integer displayOrder,
        Boolean isActive
) {}