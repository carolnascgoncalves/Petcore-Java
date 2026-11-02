package br.com.fiap.petcore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.petcore.model.Endereco;


public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
