package br.com.fiap.petcore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.petcore.model.Endereco;
import br.com.fiap.petcore.repository.EnderecoRepository;

import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EnderecoServiceImp implements EnderecoService{
	@Autowired
    private EnderecoRepository enderecoRepository;

    @Override
    public Endereco create(Endereco endereco){
        return this.enderecoRepository.save(endereco);
    }

    @Override
    public Optional<Endereco> update(Long id, Endereco patch){
        return enderecoRepository.findById(id)
                .map(existing -> {
                    if (patch.getCep() != null)
                        existing.setCep(patch.getCep());

                    if(patch.getComplemento() != null)
                        existing.setComplemento(patch.getComplemento());

                    return enderecoRepository.save(existing);
                });
    }

    @Override
    public void delete(Long id){
        Optional<Endereco> enderecoOP = enderecoRepository.findById(id);

        Endereco endereco = enderecoOP.get();
        if(endereco.getClinica() != null){
            throw new RuntimeException("Não é possível deletar um endereço associado à uma clínica");
        }

        enderecoRepository.deleteById(id);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<Endereco> fetchById(Long id){
        return this.enderecoRepository.findById(id);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean existsById(Long id){
        return this.enderecoRepository.existsById(id);
    }

    public Page<Endereco> fetchAll(Pageable pageable){
        return this.enderecoRepository.findAll(pageable);
    }

}