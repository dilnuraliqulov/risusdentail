package com.example.risus.controller.public_api;

import com.example.risus.dto.response.NewsResponse;
import com.example.risus.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/news")
@RequiredArgsConstructor
public class PublicNewsController {

    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<Page<NewsResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(newsService.getPublished(pageable));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<NewsResponse> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(newsService.getBySlug(slug));
    }
}