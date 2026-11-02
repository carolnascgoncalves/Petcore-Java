package br.com.fiap.petcore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.petcore.model.Historico;


public interface HistoricoRepository extends JpaRepository<Historico, Long> {
}
