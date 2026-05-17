package com.example.risus.dto.response;


import com.example.risus.entity.ServiceCategory;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ServiceCategoryResponse {

    private UUID id;
    private String slug;
    private TranslatedField name;
    private List<ServiceResponse> services;

    public static ServiceCategoryResponse from(ServiceCategory c) {
        return ServiceCategoryResponse.builder()
                .id(c.getId())
                .slug(c.getSlug())
                .name(new TranslatedField(c.getNameUz(), c.getNameRu(), c.getNameEn()))
                .services(c.getServices().stream()
                        .filter(s -> s.getIsActive())
                        .map(ServiceResponse::from)
                        .toList())
                .build();
    }
}