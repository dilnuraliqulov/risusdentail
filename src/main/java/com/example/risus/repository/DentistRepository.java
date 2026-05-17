package com.example.risus.repository;

import com.example.risus.entity.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DentistRepository extends JpaRepository<Dentist, UUID> {

    List<Dentist> findByIsActiveTrueOrderByDisplayOrderAsc();

}
