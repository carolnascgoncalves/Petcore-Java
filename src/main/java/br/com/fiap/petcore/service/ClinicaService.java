package br.com.fiap.petcore.service;

import java.util.Optional;

import br.com.fiap.petcore.model.Clinica;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClinicaService {
    Clinica create(Clinica clinica);

    Optional<Clinica> update(Long id, Clinica clinica);

    void delete(Long id);

    Page<Clinica> fetchAll(Pageable pageable);

    boolean existsById(Long id);

    Optional<Clinica> fetchById(Long id);
}
