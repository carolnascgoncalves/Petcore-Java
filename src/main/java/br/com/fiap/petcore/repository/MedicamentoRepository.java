package br.com.fiap.petcore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.petcore.model.Medicamento;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
}
