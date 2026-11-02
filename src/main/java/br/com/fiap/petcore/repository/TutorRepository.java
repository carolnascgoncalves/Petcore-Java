package br.com.fiap.petcore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.petcore.model.Tutor;

import java.util.Optional;


public interface TutorRepository  extends JpaRepository<Tutor, Long>{
    Optional<Tutor> findByEmail(String email);
    boolean existsByTelefone(String telefone);
    boolean existsByEmail(String email);
}