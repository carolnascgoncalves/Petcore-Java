package br.com.fiap.petcore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.petcore.model.Relatorio;

import java.util.Optional;

public interface RelatorioService {
    Relatorio create(Relatorio relatorio);

    Optional<Relatorio> update(Long id, Relatorio relatorio);

    Page<Relatorio> fetchAll(Pageable pageable);

    Optional<Relatorio> fetchById(Long id);

    void delete(Long id);

    boolean existsById(Long id);
}