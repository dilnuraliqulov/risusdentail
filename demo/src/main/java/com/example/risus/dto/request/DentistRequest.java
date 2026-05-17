package com.example.risus.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DentistRequest(
        @NotBlank String fullNameUz,
        @NotBlank String fullNameRu,
        @NotBlank String fullNameEn,
        String specialtyUz,
        String specialtyRu,
        String specialtyEn,
        String bioUz,
        String bioRu,
        String bioEn,
        String photoUrl,
        @NotNull Integer displayOrder,
        Boolean isActive
) {}