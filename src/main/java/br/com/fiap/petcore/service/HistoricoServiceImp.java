package br.com.fiap.petcore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.petcore.model.Historico;
import br.com.fiap.petcore.model.Pet;
import br.com.fiap.petcore.repository.HistoricoRepository;
import br.com.fiap.petcore.repository.PetRepository;
import br.com.fiap.petcore.repository.ProntuarioRepository;
import br.com.fiap.petcore.repository.RelatorioRepository;

import java.util.Optional;


@Service
@Transactional(propagation = Propagation.REQUIRED)
public class HistoricoServiceImp implements HistoricoService{
	@Autowired
    private HistoricoRepository historicoRepository;
	
	@Autowired
    private PetRepository petRepository;
	
	@Autowired
    private RelatorioRepository relatorioRepository;
	
	@Autowired
    private ProntuarioRepository prontuarioRepository;

	@Override
	public Historico create(Historico historico) {
	    Long petId = historico.getPet().getId();

	    Pet pet = petRepository.findById(petId).orElseThrow();

	    historico.setPet(pet);

	    Historico savedHistorico = historicoRepository.save(historico);

	    pet.setHistorico(savedHistorico);
	    petRepository.save(pet);

	    savedHistorico.setPet(pet);

	    return savedHistorico;
	}

    @Override
    public Optional<Historico> updateStatus(Long id, Historico patch) {
        return historicoRepository.findById(id).map(existing -> {
            if (patch.getStatus() != null) existing.setStatus(patch.getStatus());
            return historicoRepository.save(existing);
        });
    }

    @Override
    public void delete(Long id){
        Historico hist = historicoRepository.findById(id).orElseThrow();

        if(hist.getRelatorios() != null){
        hist.getRelatorios().forEach(rel -> rel.setHistorico(null));
        relatorioRepository.saveAll(hist.getRelatorios());
        }

        if(hist.getProntuarios() != null){
        hist.getProntuarios().forEach(pront -> pront.setHistorico(null));
        prontuarioRepository.saveAll(hist.getProntuarios());}


        if(hist.getPet() != null){
        hist.getPet().setHistorico(null);
        petRepository.save(hist.getPet());
        }


        historicoRepository.delete(hist);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<Historico> fetchById(Long id){
        return this.historicoRepository.findById(id);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean existsById(Long id){
        return this.historicoRepository.existsById(id);
    }

    public Page<Historico> fetchAll(Pageable pageable){
        return this.historicoRepository.findAll(pageable.isUnpaged() ? Pageable.unpaged(Sort.by(Sort.Direction.DESC, "data")) : pageable);
    }

}
