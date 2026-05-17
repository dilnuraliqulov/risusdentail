package com.example.risus.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record PriceItemRequest(
        @NotNull UUID serviceId,
        @NotBlank String labelUz,
        @NotBlank String labelRu,
        @NotBlank String labelEn,
        @NotNull @Positive BigDecimal price,
        String currency,
        @NotNull Integer displayOrder
) {}