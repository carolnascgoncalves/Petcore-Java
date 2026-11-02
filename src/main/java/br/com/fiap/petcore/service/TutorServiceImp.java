package br.com.fiap.petcore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.petcore.model.Pet;
import br.com.fiap.petcore.model.Tutor;
import br.com.fiap.petcore.repository.TutorRepository;

import java.util.Optional;

@Service
@Transactional( propagation = Propagation.REQUIRED)
public class TutorServiceImp implements TutorService{
	@Autowired
    private TutorRepository tutorRepository;

	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public Tutor create(Tutor tutor){
	    tutor.setSenha(encoder.encode(tutor.getSenha()));
	    return this.tutorRepository.save(tutor);
	}

    @Override
    public Optional<Tutor> update(Long id, Tutor patch) {
        return tutorRepository.findById(id)
                .map(existing -> {

                    if (patch.getNome() != null)
                        existing.setNome(patch.getNome());

                    if (patch.getDataNascimento() != null)
                        existing.setDataNascimento(patch.getDataNascimento());

                    if (patch.getTelefone() != null)
                        existing.setTelefone(patch.getTelefone());

                    if (patch.getEmail() != null)
                        existing.setEmail(patch.getEmail());

                    if (patch.getSexo() != null)
                        existing.setSexo(patch.getSexo());

                    if (patch.getSenha() != null && !patch.getSenha().isBlank())
                        existing.setSenha(encoder.encode(patch.getSenha()));

                    if (patch.getUrlImg() != null)
                        existing.setUrlImg(patch.getUrlImg());

                    return tutorRepository.save(existing);
                });
    }
    @Override
    public void delete(Long id){
        tutorRepository.findById(id).ifPresent(tutor -> {
            tutor.getPets().clear();
            tutorRepository.save(tutor);
            tutorRepository.delete(tutor);
        });
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<Tutor> fetchById(Long id){
        return this.tutorRepository.findById(id);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean existsById(Long id){
        return this.tutorRepository.existsById(id);
    }

    public Page<Tutor> fetchAll(Pageable pageable){
        return this.tutorRepository.findAll(pageable);
    }
    
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean existsByTelefone(String telefone) {
        return tutorRepository.existsByTelefone(telefone);
    }
    
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean existsByEmail(String email) {
        return tutorRepository.existsByEmail(email);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<Tutor> fetchByEmail(String email) {
        return tutorRepository.findByEmail(email);
    }

    @Override
    public void associatePet(Long idTutor, Pet pet) {
        tutorRepository.findById(idTutor).ifPresent(tutor -> {
            tutor.getPets().add(pet);
            tutorRepository.save(tutor);
        });
    }
}