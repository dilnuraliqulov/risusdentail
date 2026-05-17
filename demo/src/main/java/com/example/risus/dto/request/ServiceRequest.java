package com.example.risus.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ServiceRequest(
        @NotNull UUID categoryId,
        @NotBlank String slug,
        @NotBlank String titleUz,
        @NotBlank String titleRu,
        @NotBlank String titleEn,
        String descriptionUz,
        String descriptionRu,
        String descriptionEn,
        String heroImageUrl,
        @NotNull Integer displayOrder,
        Boolean isActive
) {}