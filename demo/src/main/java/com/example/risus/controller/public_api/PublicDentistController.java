package com.example.risus.controller.public_api;

import com.example.risus.dto.response.DentistResponse;
import com.example.risus.service.DentistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/dentists")
@RequiredArgsConstructor
public class PublicDentistController {

    private final DentistService dentistService;

    @GetMapping
    public ResponseEntity<List<DentistResponse>> getAll() {
        return ResponseEntity.ok(dentistService.getAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DentistResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(dentistService.getById(id));
    }
}