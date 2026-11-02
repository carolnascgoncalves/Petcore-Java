package br.com.fiap.petcore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.petcore.model.Prontuario;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
}