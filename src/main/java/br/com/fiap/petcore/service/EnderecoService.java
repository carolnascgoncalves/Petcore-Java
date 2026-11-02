package br.com.fiap.petcore.service;

import br.com.fiap.petcore.model.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EnderecoService {
    Endereco create(Endereco endereco);

    Optional<Endereco> update(Long id, Endereco endereco);

    void delete(Long id);

    Page<Endereco> fetchAll(Pageable pageable);

    Optional<Endereco> fetchById(Long id);

    boolean existsById(Long id);
}
