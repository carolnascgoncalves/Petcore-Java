package br.com.fiap.petcore.security;

import br.com.fiap.petcore.model.Medico;
import br.com.fiap.petcore.model.Tutor;
import br.com.fiap.petcore.repository.MedicoRepository;
import br.com.fiap.petcore.repository.TutorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Tutor> tutor = tutorRepository.findByEmail(email);

        if (tutor.isPresent()) {
            return User.withUsername(tutor.get().getEmail())
                    	.password(tutor.get().getSenha())
                    	.roles("TUTOR").build();
        }

        Optional<Medico> medico = medicoRepository.findByEmail(email);
        if (medico.isPresent()) {
            return User.withUsername(medico.get().getEmail())
                    	.password(medico.get().getSenha())
                    	.roles("MEDICO").build();
        }

        throw new UsernameNotFoundException(
                "Usuário não encontrado: " + email
        );
    }
}