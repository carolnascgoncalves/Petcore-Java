package br.com.fiap.petcore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.petcore.model.Medico;

import java.util.Optional;

public interface MedicoService {
    Medico create(Medico medico);

    Optional<Medico> update(Long id, Medico medico);

    void delete(Long id);

    Page<Medico> fetchAll(Pageable pageable);

    Optional<Medico> fetchById(Long id);

    boolean existsById(Long id);
    
    boolean existsByTelefone(String telefone);
    
    boolean existsByEmail(String email);

    Optional<Medico> fetchByEmail(String email);
}
