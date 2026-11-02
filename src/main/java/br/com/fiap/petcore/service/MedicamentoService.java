package br.com.fiap.petcore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.petcore.model.Medicamento;

import java.util.Optional;

public interface MedicamentoService {
    Medicamento create(Medicamento medicamento);

    Optional<Medicamento> update(Long id, Medicamento medic);

    void delete(Long id);

    Page<Medicamento> fetchAll(Pageable pageable);

    Optional<Medicamento> fetchById(Long id);

    boolean existsById(Long id);
}