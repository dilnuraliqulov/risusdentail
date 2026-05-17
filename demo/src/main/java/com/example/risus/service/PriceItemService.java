package com.example.risus.service;

import com.example.risus.dto.request.PriceItemRequest;
import com.example.risus.dto.response.PriceItemResponse;
import com.example.risus.entity.PriceItem;
import com.example.risus.entity.Service;
import com.example.risus.exception.ResourceNotFoundException;
import com.example.risus.repository.PriceItemRepository;
import com.example.risus.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class PriceItemService {

    private final PriceItemRepository priceItemRepository;
    private final ServiceRepository serviceRepository;

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<PriceItemResponse> getByService(UUID serviceId) {
        return priceItemRepository.findByServiceIdOrderByDisplayOrderAsc(serviceId)
                .stream()
                .map(PriceItemResponse::from)
                .toList();
    }

    @org.springframework.transaction.annotation.Transactional
    public PriceItemResponse create(PriceItemRequest request) {
        Service service = serviceRepository.findById(request.serviceId())
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + request.serviceId()));

        PriceItem item = PriceItem.builder()
                .service(service)
                .labelUz(request.labelUz())
                .labelRu(request.labelRu())
                .labelEn(request.labelEn())
                .price(request.price())
                .currency(request.currency() != null ? request.currency() : "UZS")
                .displayOrder(request.displayOrder())
                .build();

        return PriceItemResponse.from(priceItemRepository.save(item));
    }

    @org.springframework.transaction.annotation.Transactional
    public PriceItemResponse update(UUID id, PriceItemRequest request) {
        PriceItem item = priceItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Price item not found with id: " + id));

        item.setLabelUz(request.labelUz());
        item.setLabelRu(request.labelRu());
        item.setLabelEn(request.labelEn());
        item.setPrice(request.price());
        if (request.currency() != null) item.setCurrency(request.currency());
        item.setDisplayOrder(request.displayOrder());

        return PriceItemResponse.from(priceItemRepository.save(item));
    }

    @org.springframework.transaction.annotation.Transactional
    public void delete(UUID id) {
        if (!priceItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Price item not found with id: " + id);
        }
        priceItemRepository.deleteById(id);
    }
}
