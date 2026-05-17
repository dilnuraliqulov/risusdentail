package com.example.risus.controller.admin;

import com.example.risus.dto.request.DentistRequest;
import com.example.risus.dto.response.DentistResponse;
import com.example.risus.service.DentistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/dentists")
@RequiredArgsConstructor
public class AdminDentistController {

    private final DentistService dentistService;

    @GetMapping
    public ResponseEntity<List<DentistResponse>> getAll() {
        return ResponseEntity.ok(dentistService.getAll());
    }

    @PostMapping
    public ResponseEntity<DentistResponse> create(@Valid @RequestBody DentistRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dentistService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DentistResponse> update(@PathVariable UUID id,
                                                  @Valid @RequestBody DentistRequest request) {
        return ResponseEntity.ok(dentistService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        dentistService.delete(id);
        return ResponseEntity.noContent().build();
    }
}