package com.example.risus.service;

import com.example.risus.dto.request.DentistRequest;
import com.example.risus.dto.response.DentistResponse;
import com.example.risus.entity.Dentist;
import com.example.risus.exception.ResourceNotFoundException;
import com.example.risus.repository.DentistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DentistService {

    private final DentistRepository dentistRepository;

    // ── Public ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<DentistResponse> getAllActive() {
        return dentistRepository.findByIsActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(DentistResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public DentistResponse getById(UUID id) {
        return dentistRepository.findById(id)
                .filter(Dentist::getIsActive)
                .map(DentistResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Dentist not found with id: " + id));
    }

    // ── Admin ─────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<DentistResponse> getAll() {
        return dentistRepository.findAll()
                .stream()
                .map(DentistResponse::from)
                .toList();
    }

    @Transactional
    public DentistResponse create(DentistRequest request) {
        Dentist dentist = Dentist.builder()
                .fullNameUz(request.fullNameUz())
                .fullNameRu(request.fullNameRu())
                .fullNameEn(request.fullNameEn())
                .specialtyUz(request.specialtyUz())
                .specialtyRu(request.specialtyRu())
                .specialtyEn(request.specialtyEn())
                .bioUz(request.bioUz())
                .bioRu(request.bioRu())
                .bioEn(request.bioEn())
                .photoUrl(request.photoUrl())
                .displayOrder(request.displayOrder())
                .isActive(request.isActive() != null ? request.isActive() : true)
                .build();

        return DentistResponse.from(dentistRepository.save(dentist));
    }

    @Transactional
    public DentistResponse update(UUID id, DentistRequest request) {
        Dentist dentist = dentistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dentist not found with id: " + id));

        dentist.setFullNameUz(request.fullNameUz());
        dentist.setFullNameRu(request.fullNameRu());
        dentist.setFullNameEn(request.fullNameEn());
        dentist.setSpecialtyUz(request.specialtyUz());
        dentist.setSpecialtyRu(request.specialtyRu());
        dentist.setSpecialtyEn(request.specialtyEn());
        dentist.setBioUz(request.bioUz());
        dentist.setBioRu(request.bioRu());
        dentist.setBioEn(request.bioEn());
        dentist.setPhotoUrl(request.photoUrl());
        dentist.setDisplayOrder(request.displayOrder());
        if (request.isActive() != null) dentist.setIsActive(request.isActive());

        return DentistResponse.from(dentistRepository.save(dentist));
    }

    @Transactional
    public void delete(UUID id) {
        if (!dentistRepository.existsById(id)) {
            throw new ResourceNotFoundException("Dentist not found with id: " + id);
        }
        dentistRepository.deleteById(id);
    }
}
