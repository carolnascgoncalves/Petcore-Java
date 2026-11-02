package br.com.fiap.petcore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.petcore.model.Exame;

import java.util.Optional;

public interface ExameService {
    Exame create(Exame exame);

    Optional<Exame> update(Long id, Exame exame);

    void delete(Long id);

    Page<Exame> fetchAll(Pageable pageable);

    Optional<Exame> fetchById(Long id);

    boolean existsById(Long id);
}