package br.com.fiap.petcore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.petcore.model.Medico;

import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByEmail(String email);
    boolean existsByTelefone(String telefone);
    boolean existsByEmail(String email);
}