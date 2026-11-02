package br.com.fiap.petcore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.petcore.model.Relatorio;

public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {
}