package br.com.fiap.petcore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.petcore.model.Relatorio;
import br.com.fiap.petcore.repository.ClinicaRepository;
import br.com.fiap.petcore.repository.RelatorioRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional( propagation = Propagation.REQUIRED)
public class RelatorioServiceImp implements RelatorioService{
	@Autowired
    private RelatorioRepository relatorioRepository;
	
	@Autowired
    private ClinicaRepository clinicaRepository;

    @Override
    public Relatorio create(Relatorio relatorio) {
        relatorio.setData(LocalDateTime.now());
        return relatorioRepository.save(relatorio);
    }

    @Override
    public Optional<Relatorio> update(Long id, Relatorio relatorio) {
        return relatorioRepository.findById(id)
                .map(existing -> {

                    if (relatorio.getObservacao() != null)
                        existing.setObservacao(relatorio.getObservacao());

                    if (relatorio.getHistorico() != null)
                        existing.setHistorico(relatorio.getHistorico());

                    if (relatorio.getMedico() != null)
                        existing.setMedico(relatorio.getMedico());

                    return relatorioRepository.save(existing);
                });
    }

    @Transactional( propagation = Propagation.NEVER)
    @Override
    public Page<Relatorio> fetchAll(Pageable pageable) {
        return this.relatorioRepository.findAll(pageable.isUnpaged() ? Pageable.unpaged(Sort.by(Sort.Direction.DESC, "data")) : pageable);
    }

    @Transactional( propagation = Propagation.NEVER)
    @Override
    public Optional<Relatorio> fetchById(Long id) {
        return this.relatorioRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        Relatorio rel = relatorioRepository.findById(id).orElseThrow();
        rel.getClinicas().forEach(cli -> cli.getRelatorios().remove(rel));
        clinicaRepository.saveAll(rel.getClinicas());
        rel.setClinicas(null);

        relatorioRepository.delete(rel);
    }

    public boolean existsById(Long id){
        return this.relatorioRepository.existsById(id);
    }
}