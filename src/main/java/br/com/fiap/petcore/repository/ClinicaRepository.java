package br.com.fiap.petcore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.petcore.model.Clinica;


public interface ClinicaRepository extends JpaRepository<Clinica, Long> {
    boolean existsByEnderecoId(Long enderecoId);
}