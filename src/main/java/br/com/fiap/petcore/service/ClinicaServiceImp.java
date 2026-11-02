package br.com.fiap.petcore.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.petcore.model.Clinica;
import br.com.fiap.petcore.model.Endereco;
import br.com.fiap.petcore.repository.ClinicaRepository;
import br.com.fiap.petcore.repository.EnderecoRepository;
import br.com.fiap.petcore.repository.RelatorioRepository;

import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ClinicaServiceImp implements ClinicaService{
	@Autowired
    private ClinicaRepository clinicaRepository;
	
	@Autowired
    private RelatorioRepository relatorioRepository;
	
	@Autowired
    private EnderecoRepository enderecoRepository;

    @Override
    public Clinica create(Clinica clinica){
        Endereco endereco = enderecoRepository.save(clinica.getEndereco());
        clinica.setEndereco(endereco);
        endereco.setClinica(clinica);
        return this.clinicaRepository.save(clinica);
    }

    @Override
    public Optional<Clinica> update(Long id, Clinica patch){
        return clinicaRepository.findById(id)
                .map(existing -> {
                    if (patch.getNome() != null) existing.setNome(patch.getNome());

                    if (patch.getEndereco() != null) {
                        Endereco endereco = existing.getEndereco();
                        if (endereco == null) endereco = new Endereco();
                        endereco.setCep(patch.getEndereco().getCep());
                        endereco.setComplemento(patch.getEndereco().getComplemento());
                        endereco = enderecoRepository.save(endereco);
                        existing.setEndereco(endereco);
                    }

                    return clinicaRepository.save(existing);
                });
    }


    @Override
    public void delete(Long id){
        Clinica cli = clinicaRepository.findById(id).orElseThrow();

        cli.getRelatorios().forEach(rel -> rel.setClinicas(null));
        relatorioRepository.saveAll(cli.getRelatorios());

        if (cli.getEndereco() != null) {
            cli.getEndereco().setClinica(null);
            enderecoRepository.save(cli.getEndereco());
        }

        clinicaRepository.delete(cli);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<Clinica> fetchById(Long id){
        return this.clinicaRepository.findById(id);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean existsById(Long id){
        return this.clinicaRepository.existsById(id);
    }

    public Page<Clinica> fetchAll(Pageable pageable){
        return this.clinicaRepository.findAll(pageable);
    }

}