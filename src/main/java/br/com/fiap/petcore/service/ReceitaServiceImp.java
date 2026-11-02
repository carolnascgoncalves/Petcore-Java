package br.com.fiap.petcore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.petcore.model.Medicamento;
import br.com.fiap.petcore.model.Receita;
import br.com.fiap.petcore.repository.MedicamentoRepository;
import br.com.fiap.petcore.repository.PetRepository;
import br.com.fiap.petcore.repository.ReceitaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional( propagation = Propagation.REQUIRED)
public class ReceitaServiceImp implements ReceitaService{
	@Autowired
    private ReceitaRepository receitaRepository;
	@Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private PetRepository petRepository;

    @Override
    public Receita create(Receita receita) {

        Set<Medicamento> medicamentos = receita.getMedicamentos()
                .stream()
                .map(med -> medicamentoRepository.findById(med.getId()).orElse(null))
                .collect(Collectors.toSet());

        receita.setMedicamentos(medicamentos);
        if (receita.getPet() != null) receita.setPet(petRepository.findById(receita.getPet().getId()).orElseThrow());

        return this.receitaRepository.save(receita);
    }

    @Override
    @Transactional( propagation = Propagation.NEVER)
    public Optional<Receita> fetchById(Long id) {
        return this.receitaRepository.findById(id);
    }

    @Override
    @Transactional( propagation = Propagation.NEVER)
    public Page<Receita> fetchAll(Pageable pageable) {
        return this.receitaRepository.findAllByRemovidaFalseAndValidadeGreaterThanEqualOrderByDataDesc(LocalDate.now(), pageable);
    }

    @Override
    public void delete(Long id) {
        receitaRepository.findById(id).ifPresent(receita -> {
            if (receita.getProntuario() != null || receita.getPet() != null) {
                receita.setRemovida(true);
                receitaRepository.save(receita);
            } else {
                receitaRepository.delete(receita);
            }
        });
    }


    @Override
    public boolean existsById(Long id){return this.receitaRepository.existsByIdAndRemovidaFalse(id);}
}
