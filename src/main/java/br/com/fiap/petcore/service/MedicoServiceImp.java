package br.com.fiap.petcore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.petcore.model.Medico;
import br.com.fiap.petcore.repository.ExameRepository;
import br.com.fiap.petcore.repository.MedicoRepository;
import br.com.fiap.petcore.repository.ProntuarioRepository;
import br.com.fiap.petcore.repository.ReceitaRepository;
import br.com.fiap.petcore.repository.RelatorioRepository;

import java.util.Optional;

@Service
@Transactional( propagation = Propagation.REQUIRED)
public class MedicoServiceImp implements MedicoService{
	@Autowired
    private MedicoRepository medicoRepository;
	
	@Autowired
    private ExameRepository exameRepository;

	@Autowired
    private RelatorioRepository relatorioRepository;
	
	@Autowired
    private ProntuarioRepository prontuarioRepository;
	
	@Autowired
    private ReceitaRepository receitaRepository;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public Medico create(Medico medico){
	    medico.setSenha(encoder.encode(medico.getSenha()));
	    return this.medicoRepository.save(medico);
	}

    @Override
    public Optional<Medico> update(Long id, Medico patch){
        return medicoRepository.findById(id)
                .map(existing -> {

                    if (patch.getEmail() != null)
                        existing.setEmail(patch.getEmail());

                    if (patch.getTelefone() != null)
                        existing.setTelefone(patch.getTelefone());
                    
                    if (patch.getSenha() != null && !patch.getSenha().isBlank())
                        existing.setSenha(encoder.encode(patch.getSenha()));

                    if (patch.getUrlImg() != null)
                        existing.setUrlImg(patch.getUrlImg());

                    return medicoRepository.save(existing);
                });
    }

    @Override
    public void delete(Long id) {
        Medico med = medicoRepository.findById(id).orElseThrow();

        med.getProntuarios().forEach(pront -> pront.setMedico(null));
        prontuarioRepository.saveAll(med.getProntuarios());

        med.getExames().forEach(ex -> ex.setMedico(null));
        exameRepository.saveAll(med.getExames());

        med.getReceitas().forEach(rec -> rec.setMedico(null));
        receitaRepository.saveAll(med.getReceitas());

        med.getRelatorios().forEach(rel -> rel.setMedico(null));
        relatorioRepository.saveAll(med.getRelatorios());

        medicoRepository.delete(med);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<Medico> fetchById(Long id){
        return this.medicoRepository.findById(id);
    }
    

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean existsById(Long id){
        return this.medicoRepository.existsById(id);
    }

    @Override
    public Page<Medico> fetchAll(Pageable pageable){
        return this.medicoRepository.findAll(pageable);
    }
    
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean existsByTelefone(String telefone) {
        return medicoRepository.existsByTelefone(telefone);
    }
    
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean existsByEmail(String email) {
        return medicoRepository.existsByEmail(email);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<Medico> fetchByEmail(String email) {
        return medicoRepository.findByEmail(email);
    }
}