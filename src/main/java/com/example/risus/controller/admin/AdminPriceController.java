package com.example.risus.controller.admin;

import com.example.risus.dto.request.PriceItemRequest;
import com.example.risus.dto.response.PriceItemResponse;
import com.example.risus.service.PriceItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/prices")
@RequiredArgsConstructor
public class AdminPriceController {

    private final PriceItemService priceItemService;

    @GetMapping("/by-service/{serviceId}")
    public ResponseEntity<List<PriceItemResponse>> getByService(@PathVariable UUID serviceId) {
        return ResponseEntity.ok(priceItemService.getByService(serviceId));
    }

    @PostMapping
    public ResponseEntity<PriceItemResponse> create(@Valid @RequestBody PriceItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(priceItemService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PriceItemResponse> update(@PathVariable UUID id,
                                                    @Valid @RequestBody PriceItemRequest request) {
        return ResponseEntity.ok(priceItemService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        priceItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}