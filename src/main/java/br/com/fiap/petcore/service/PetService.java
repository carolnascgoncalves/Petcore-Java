package br.com.fiap.petcore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.petcore.model.Pet;

import java.util.Optional;

public interface PetService {
    Pet create(Pet pet);

    Optional<Pet> updateStatus(Long id, Pet pet);

    Optional<Pet> updateImage(Long id, Pet pet);

    Page<Pet> fetchAll(Pageable pageable);

    Page<Pet> fetchMenuAll(Pageable pageable);

    Optional<Pet> fetchById(Long id);

    boolean existsById(Long id);

    void delete(Long id);
}