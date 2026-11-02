package br.com.fiap.petcore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.petcore.model.Receita;

import java.util.Optional;

public interface ReceitaService {
    Receita create(Receita receita);

    Optional<Receita> fetchById(Long id);

    Page<Receita> fetchAll(Pageable pageable);

    void delete(Long id);

    boolean existsById(Long id);
}