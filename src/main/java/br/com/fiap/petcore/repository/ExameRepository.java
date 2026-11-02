package br.com.fiap.petcore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.petcore.model.Exame;

import java.time.LocalDateTime;

public interface ExameRepository extends JpaRepository<Exame, Long> {
    Page<Exame> findAllByRemovidoFalseAndDataGreaterThanEqualOrderByDataAsc(LocalDateTime data, Pageable pageable);
    boolean existsByIdAndRemovidoFalse(Long id);
}
