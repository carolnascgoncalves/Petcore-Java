package br.com.fiap.petcore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.petcore.model.Exame;
import br.com.fiap.petcore.model.Historico;
import br.com.fiap.petcore.model.Medico;
import br.com.fiap.petcore.model.Prontuario;
import br.com.fiap.petcore.model.Receita;
import br.com.fiap.petcore.repository.ExameRepository;
import br.com.fiap.petcore.repository.HistoricoRepository;
import br.com.fiap.petcore.repository.MedicoRepository;
import br.com.fiap.petcore.repository.ProntuarioRepository;
import br.com.fiap.petcore.repository.ReceitaRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional( propagation = Propagation.REQUIRED)
public class ProntuarioServiceImp implements ProntuarioService{
	
	@Autowired
    private ProntuarioRepository prontuarioRepository;
	
	@Autowired
    private MedicoRepository medicoRepository;
	
	@Autowired
    private HistoricoRepository historicoRepository;
	
	@Autowired
    private ExameRepository exameRepository;
	
	@Autowired
    private ReceitaRepository receitaRepository;

    @Override
    public Prontuario create(Prontuario prontuario) {
        Long medicoId = prontuario.getMedico().getId();
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow();

        Long historicoId = prontuario.getHistorico().getId();
        Historico historico = historicoRepository.findById(historicoId)
                .orElseThrow();

        Set<Exame> exames = prontuario.getExames() != null
                ? prontuario.getExames()
                        .stream()
                        .map(exame -> exameRepository.findById(exame.getId()).orElseThrow())
                        .collect(Collectors.toSet())
                : Set.of();

        Set<Receita> receitas = prontuario.getReceitas() != null
                ? prontuario.getReceitas()
                        .stream()
                        .map(receita -> receitaRepository.findById(receita.getId()).orElseThrow())
                        .collect(Collectors.toSet())
                : Set.of();

        prontuario.setMedico(medico);
        prontuario.setHistorico(historico);
        prontuario.setExames(exames);
        prontuario.setReceitas(receitas);

        Prontuario savedProntuario = prontuarioRepository.save(prontuario);


        for (Exame exame : exames) {
            exame.setProntuario(savedProntuario);
            exameRepository.save(exame);
        }


        for (Receita receita : receitas) {
            receita.setProntuario(savedProntuario);
            receitaRepository.save(receita);
        }


        if(historico.getProntuarios() != null) {historico.getProntuarios().add(savedProntuario);
        } else {historico.setProntuarios(Set.of(savedProntuario));}

        historicoRepository.save(historico);


        savedProntuario.setExames(exames);
        savedProntuario.setReceitas(receitas);
        savedProntuario.setHistorico(historico);
        savedProntuario.setMedico(medico);

        return savedProntuario;
    }

    @Override
    public Optional<Prontuario> update(Long id, Prontuario patch) {
        return prontuarioRepository.findById(id)
                .map(existing -> {

                    if (patch.getDescricao() != null)
                        existing.setDescricao(patch.getDescricao());

                    return prontuarioRepository.save(existing);
                });
    }

    @Override
    public Page<Prontuario> fetchAll(Pageable pageable) {
        return prontuarioRepository.findAll(pageable.isUnpaged() ? Pageable.unpaged(Sort.by(Sort.Direction.DESC, "data")) : pageable);
    }

    @Override
    public Optional<Prontuario> fetchById(Long id) {
        return prontuarioRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id){
        return prontuarioRepository.existsById(id);
    }

    @Override
    public void delete(Long id){

        Prontuario pront = prontuarioRepository.findById(id).orElseThrow();

        if(pront.getExames() != null){
            pront.getExames().forEach(ex -> ex.setProntuario(null));
            exameRepository.saveAll(pront.getExames());
        }

        if(pront.getReceitas() != null){
            pront.getReceitas().forEach(rec -> rec.setProntuario(null));
            receitaRepository.saveAll(pront.getReceitas());
        }

        if(pront.getHistorico() != null){
            pront.getHistorico().getProntuarios().remove(pront);
            historicoRepository.save(pront.getHistorico());
        }

        if(pront.getMedico() != null){
            pront.getMedico().getProntuarios().remove(pront);
            medicoRepository.save(pront.getMedico());
        }

        prontuarioRepository.delete(pront);
    }

}