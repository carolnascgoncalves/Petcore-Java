package br.com.fiap.petcore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.petcore.model.Historico;

import java.util.Optional;

public interface HistoricoService {
    Historico create(Historico historico);

    void delete(Long id);

    Page<Historico> fetchAll(Pageable pageable);

    Optional<Historico> fetchById(Long id);

    Optional<Historico> updateStatus(Long id, Historico historico);

    boolean existsById(Long id);
}
