package com.example.risus.dto.request;


import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record NewsRequest(
        @NotBlank String slug,
        @NotBlank String titleUz,
        @NotBlank String titleRu,
        @NotBlank String titleEn,
        @NotBlank String bodyUz,
        @NotBlank String bodyRu,
        @NotBlank String bodyEn,
        String coverImageUrl,
        Boolean isPublished,
        LocalDateTime publishedAt
) {}