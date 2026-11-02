package br.com.fiap.petcore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.petcore.model.Receita;

import java.time.LocalDate;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    Page<Receita> findAllByRemovidaFalseAndValidadeGreaterThanEqualOrderByDataDesc(LocalDate validade, Pageable pageable);
    boolean existsByIdAndRemovidaFalse(Long id);
}
