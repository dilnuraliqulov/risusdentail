package com.example.risus.dto.response;


import com.example.risus.entity.PriceItem;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class PriceItemResponse {

    private UUID id;
    private TranslatedField label;
    private BigDecimal price;
    private String currency;
    private Integer displayOrder;

    public static PriceItemResponse from(PriceItem p) {
        return PriceItemResponse.builder()
                .id(p.getId())
                .label(new TranslatedField(p.getLabelUz(), p.getLabelRu(), p.getLabelEn()))
                .price(p.getPrice())
                .currency(p.getCurrency())
                .displayOrder(p.getDisplayOrder())
                .build();
    }
}