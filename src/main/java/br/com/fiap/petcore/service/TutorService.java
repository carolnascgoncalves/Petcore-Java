package br.com.fiap.petcore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.petcore.model.Pet;
import br.com.fiap.petcore.model.Tutor;

import java.util.Optional;

public interface TutorService {
    Tutor create(Tutor tutor);

    Optional<Tutor> update(Long id, Tutor tutor);

    void delete(Long id);

    Page<Tutor> fetchAll(Pageable pageable);

    Optional<Tutor> fetchById(Long id);

    boolean existsById(Long id);
    
    boolean existsByTelefone(String telefone);
    
    boolean existsByEmail(String email);

    Optional<Tutor> fetchByEmail(String email);

    void associatePet(Long idTutor, Pet pet);
}