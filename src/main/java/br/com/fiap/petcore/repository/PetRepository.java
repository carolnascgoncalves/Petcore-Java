package br.com.fiap.petcore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.petcore.model.Pet;

public interface PetRepository  extends JpaRepository<Pet, Long> {
}