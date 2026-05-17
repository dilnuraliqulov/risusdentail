package com.example.risus.dto.response;


import com.example.risus.entity.News;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class NewsResponse {

    private UUID id;
    private String slug;
    private TranslatedField title;
    private TranslatedField body;
    private String coverImageUrl;
    private LocalDateTime publishedAt;

    public static NewsResponse from(News n) {
        return NewsResponse.builder()
                .id(n.getId())
                .slug(n.getSlug())
                .title(new TranslatedField(n.getTitleUz(), n.getTitleRu(), n.getTitleEn()))
                .body(new TranslatedField(n.getBodyUz(), n.getBodyRu(), n.getBodyEn()))
                .coverImageUrl(n.getCoverImageUrl())
                .publishedAt(n.getPublishedAt())
                .build();
    }
}
