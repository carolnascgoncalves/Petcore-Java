package br.com.fiap.petcore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.petcore.model.Prontuario;

import java.util.Optional;

public interface ProntuarioService {
    Prontuario create(Prontuario prontuario);

    Optional<Prontuario> update(Long id, Prontuario prontuario);

    Page<Prontuario> fetchAll(Pageable pageable);

    Optional<Prontuario> fetchById(Long id);

    boolean existsById(Long id);

    void delete(Long id);

}
