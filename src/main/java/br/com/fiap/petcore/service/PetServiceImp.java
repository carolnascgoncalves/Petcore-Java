package br.com.fiap.petcore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.petcore.model.Historico;
import br.com.fiap.petcore.model.Pet;
import br.com.fiap.petcore.model.StatusEnum;
import br.com.fiap.petcore.repository.ExameRepository;
import br.com.fiap.petcore.repository.HistoricoRepository;
import br.com.fiap.petcore.repository.PetRepository;
import br.com.fiap.petcore.repository.ReceitaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional( propagation = Propagation.REQUIRED)
public class PetServiceImp implements PetService {
	@Autowired
    private PetRepository petRepository;
	
	@Autowired
    private HistoricoRepository historicoRepository;

    @Autowired
    private ExameRepository exameRepository;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Override
    public Pet create(Pet pet) {
        Historico historico = new Historico();
        historico.setData(LocalDateTime.now());
        historico.setStatus(StatusEnum.ATIVO);
        Historico historicoSalvo = historicoRepository.save(historico);

        pet.setHistorico(historicoSalvo);
        Pet petSalvo = petRepository.save(pet);
        historicoSalvo.setPet(petSalvo);

        return petSalvo;
    }

    @Override
    public Optional<Pet> updateStatus(Long id, Pet patch) {
        return petRepository.findById(id)
                .map(existing -> {

                    if (existing.getStatus() == StatusEnum.INATIVO) return existing;

                    if (patch.getStatus() != null) {
                        existing.setStatus(patch.getStatus());

                        if (patch.getStatus() == StatusEnum.INATIVO) {
                            if (existing.getHistorico() != null) {
                                existing.getHistorico().setStatus(StatusEnum.INATIVO);
                                historicoRepository.save(existing.getHistorico());
                            }

                            if (existing.getExames() != null) {
                                existing.getExames().forEach(exame -> exame.setRemovido(true));
                                exameRepository.saveAll(existing.getExames());
                            }

                            if (existing.getReceitas() != null) {
                                existing.getReceitas().forEach(receita -> receita.setRemovida(true));
                                receitaRepository.saveAll(existing.getReceitas());
                            }
                        }
                    }

                    return petRepository.save(existing);
                });
    }

    @Override
    public Optional<Pet> updateImage(Long id, Pet patch) {
        return petRepository.findById(id)
                .map(existing -> {

                    if (existing.getStatus() == StatusEnum.INATIVO) return existing;

                    if (patch.getUrlImg() != null)
                        existing.setUrlImg(patch.getUrlImg());

                    return petRepository.save(existing);
                });
    }


    @Override
    @Transactional(propagation = Propagation.NEVER)
    public Page<Pet> fetchAll(Pageable pageable){
        return this.petRepository.findAll(pageable);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public Page<Pet> fetchMenuAll(Pageable pageable){
        return this.petRepository.findAll(pageable);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public Optional<Pet> fetchById(Long id){
        return this.petRepository.findById(id);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean existsById(Long id) {
        return this.petRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        Pet pet = petRepository.findById(id).orElseThrow();

        if (pet.getHistorico() != null) {
            pet.getHistorico().setPet(null);
            historicoRepository.save(pet.getHistorico());
        }

        petRepository.delete(pet);
    }
}