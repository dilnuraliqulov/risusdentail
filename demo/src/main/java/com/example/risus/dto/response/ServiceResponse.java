package com.example.risus.dto.response;

import com.example.risus.entity.Service;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ServiceResponse {

    private UUID id;
    private String slug;
    private TranslatedField title;
    private TranslatedField description;
    private String heroImageUrl;
    private Integer displayOrder;
    private UUID categoryId;
    private List<PriceItemResponse> priceItems;

    public static ServiceResponse from(Service s) {
        return ServiceResponse.builder()
                .id(s.getId())
                .slug(s.getSlug())
                .title(new TranslatedField(s.getTitleUz(), s.getTitleRu(), s.getTitleEn()))
                .description(new TranslatedField(s.getDescriptionUz(), s.getDescriptionRu(), s.getDescriptionEn()))
                .heroImageUrl(s.getHeroImageUrl())
                .displayOrder(s.getDisplayOrder())
                .categoryId(s.getCategory().getId())
                .priceItems(s.getPriceItems().stream()
                        .map(PriceItemResponse::from)
                        .toList())
                .build();
    }
}