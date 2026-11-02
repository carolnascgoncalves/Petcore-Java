package br.com.fiap.petcore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.petcore.model.Exame;
import br.com.fiap.petcore.repository.ExameRepository;
import br.com.fiap.petcore.repository.PetRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ExameServiceImp implements ExameService{
	@Autowired
	private ExameRepository exameRepository;

    @Autowired
    private PetRepository petRepository;

    @Override
    public Exame create(Exame exame){
        if (exame.getPet() != null) exame.setPet(petRepository.findById(exame.getPet().getId()).orElseThrow());
        return this.exameRepository.save(exame);
    }

    @Override
    public Optional<Exame> update(Long id, Exame patch){
        return exameRepository.findById(id)
                .map(existing -> {

                    if (patch.getNome() != null)
                        existing.setNome(patch.getNome());

                    if (patch.getData() != null)
                        existing.setData(patch.getData());

                    if (patch.getTipo() != null)
                        existing.setTipo(patch.getTipo());

                    return exameRepository.save(existing);
                });
    }

    @Override
    public void delete(Long id){
        exameRepository.findById(id).ifPresent(exame -> {
            if (exame.getProntuario() != null || exame.getPet() != null) {
                exame.setRemovido(true);
                exameRepository.save(exame);
            } else {
                exameRepository.delete(exame);
            }
        });
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<Exame> fetchById(Long id){
        return this.exameRepository.findById(id);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean existsById(Long id){
        return this.exameRepository.existsByIdAndRemovidoFalse(id);
    }

    public Page<Exame> fetchAll(Pageable pageable){
        return this.exameRepository.findAllByRemovidoFalseAndDataGreaterThanEqualOrderByDataAsc(LocalDateTime.now(), pageable);
    }

}
