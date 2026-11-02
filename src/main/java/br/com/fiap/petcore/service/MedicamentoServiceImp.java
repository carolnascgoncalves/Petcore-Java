package br.com.fiap.petcore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.petcore.model.Medicamento;
import br.com.fiap.petcore.repository.MedicamentoRepository;
import br.com.fiap.petcore.repository.ReceitaRepository;

import java.util.Optional;

@Service
@Transactional( propagation = Propagation.REQUIRED)
public class MedicamentoServiceImp implements MedicamentoService{
	@Autowired
    private MedicamentoRepository medicamentoRepository;
	
	@Autowired
    private ReceitaRepository receitaRepository;

    @Override
    public Medicamento create(Medicamento medicamento){
        return this.medicamentoRepository.save(medicamento);
    }

    @Override
    public Optional<Medicamento> update(Long id, Medicamento patch){
        return medicamentoRepository.findById(id)
                .map(existing -> {
                    if (patch.getDosagem() != null)
                        existing.setDosagem(patch.getDosagem());

                    return medicamentoRepository.save(existing);
                });
    }

    @Override
    public void delete(Long id){
        Medicamento medic = medicamentoRepository.findById(id).orElseThrow();

        medic.getReceitas().forEach(rec -> {rec.getMedicamentos().remove(medic);});
        receitaRepository.saveAll(medic.getReceitas());

        medicamentoRepository.delete(medic);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<Medicamento> fetchById(Long id){
        return this.medicamentoRepository.findById(id);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean existsById(Long id){
        return this.medicamentoRepository.existsById(id);
    }

    public Page<Medicamento> fetchAll(Pageable pageable){
        return this.medicamentoRepository.findAll(pageable);
    }

}