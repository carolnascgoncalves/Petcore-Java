package br.com.fiap.petcore.control;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.petcore.control.dtos.MedicoCreateRequest;
import br.com.fiap.petcore.control.dtos.UserDadosRequest;
import br.com.fiap.petcore.model.Medico;
import br.com.fiap.petcore.service.MedicoService;
import br.com.fiap.petcore.service.TutorService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class MedicoResouce {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private TutorService tutorService;

    @GetMapping("/medico/novo")
    public ModelAndView retornarPaginaCadastro() {
        ModelAndView mv = new ModelAndView("/medico/novo");
        mv.addObject("medico", new MedicoCreateRequest());
        return mv;
    }

    @PostMapping("/medico/cadastrar")
    public ModelAndView cadastrarMedico(@Valid @ModelAttribute("medico") MedicoCreateRequest medicoRequest,BindingResult bd) {
        if (medicoRequest.getDataNascimento() != null) {
            LocalDate hoje = LocalDate.now();
            if (!medicoRequest.getDataNascimento().isBefore(hoje)) {
                bd.rejectValue("dataNascimento","dataNascimento.invalida","A data de nascimento não pode ser hoje nem uma data futura.");
            } else if (medicoRequest.getDataNascimento().isAfter(hoje.minusYears(18))) {
                bd.rejectValue("dataNascimento","idade.invalida","O médico deve ter pelo menos 18 anos.");
            }
        }

        if (medicoRequest.getTelefone() != null && !medicoRequest.getTelefone().isBlank() && (medicoService.existsByTelefone(medicoRequest.getTelefone()) || tutorService.existsByTelefone(medicoRequest.getTelefone()))) {
            bd.rejectValue("telefone","telefone.duplicado","Já existe um usuário cadastrado com este telefone.");
        }
        if (medicoRequest.getEmail() != null && !medicoRequest.getEmail().isBlank() && (medicoService.existsByEmail(medicoRequest.getEmail()) || tutorService.existsByEmail(medicoRequest.getEmail()))) {
            bd.rejectValue("email","email.duplicado","Já existe um usuário cadastrado com este email.");
        }
        if (bd.hasErrors()) return new ModelAndView("/medico/novo");

        medicoService.create(MedicoCreateRequest.toEntity(medicoRequest));
        return new ModelAndView("redirect:/login?cadastro=true");
    }

    @GetMapping("/medico/minha-conta")
    public ModelAndView minhaConta(Authentication authentication) {
        Optional<Medico> medico = medicoService.fetchByEmail(authentication.getName());
        if (medico.isPresent()) {
            ModelAndView mv = new ModelAndView("/medico/detalhes");
            mv.addObject("medico", medico.get());
            return mv;
        }
        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/medico/editar")
    public ModelAndView retornarPaginaEdicao(Authentication authentication) {
        Optional<Medico> medico = medicoService.fetchByEmail(authentication.getName());
        if (medico.isPresent()) {
            ModelAndView mv = new ModelAndView("/medico/edicao");
            UserDadosRequest dto = UserDadosRequest.toDtoMed(medico.get());
            dto.setSenha(null);
            mv.addObject("medico", dto);
            return mv;
        }
        return new ModelAndView("redirect:/home");
    }

    @PostMapping("/medico/atualizar")
    public ModelAndView atualizarMedico(@Valid @ModelAttribute("medico") UserDadosRequest dadosDto,BindingResult bd,Authentication authentication) {
        Optional<Medico> atual = medicoService.fetchByEmail(authentication.getName());
        if (atual.isEmpty()) return new ModelAndView("redirect:/home");

        if (dadosDto.getTelefone() != null && !dadosDto.getTelefone().isBlank() && ((!dadosDto.getTelefone().equals(atual.get().getTelefone()) && medicoService.existsByTelefone(dadosDto.getTelefone())) || tutorService.existsByTelefone(dadosDto.getTelefone()))) {
            bd.rejectValue("telefone","telefone.duplicado","Já existe um usuário cadastrado com este telefone.");
        }
        if (dadosDto.getEmail() != null && !dadosDto.getEmail().isBlank() && ((!dadosDto.getEmail().equals(atual.get().getEmail()) && medicoService.existsByEmail(dadosDto.getEmail())) || tutorService.existsByEmail(dadosDto.getEmail()))) {
            bd.rejectValue("email","email.duplicado","Já existe um usuário cadastrado com este email.");
        }
        if (bd.hasErrors()) return new ModelAndView("/medico/edicao");

        Optional<Medico> atualizado = medicoService.update(atual.get().getId(), UserDadosRequest.toEntityMed(dadosDto));
        if (atualizado.isPresent()) {
            UsernamePasswordAuthenticationToken novaAutenticacao = new UsernamePasswordAuthenticationToken(atualizado.get().getEmail(), authentication.getCredentials(), authentication.getAuthorities());
            novaAutenticacao.setDetails(authentication.getDetails());
            SecurityContextHolder.getContext().setAuthentication(novaAutenticacao);
        }
        return new ModelAndView("redirect:/medico/minha-conta");
    }

    @GetMapping("/medico/apagar")
    public ModelAndView apagarConta(Authentication authentication,HttpServletRequest request) throws ServletException {
        Optional<Medico> medico = medicoService.fetchByEmail(authentication.getName());
        if (medico.isPresent()) {
            List<String> associados = new ArrayList<>();
            if (medico.get().getExames() != null) medico.get().getExames().forEach(exame -> associados.add("Exame — " + exame.getNome()));
            if (medico.get().getReceitas() != null) medico.get().getReceitas().forEach(receita -> associados.add("Receita — " + receita.getNome()));
            if (medico.get().getProntuarios() != null) medico.get().getProntuarios().forEach(prontuario -> associados.add("Prontuário"));
            if (medico.get().getRelatorios() != null) medico.get().getRelatorios().forEach(relatorio -> associados.add("Relatório"));

            if (!associados.isEmpty()) {
                ModelAndView mv = new ModelAndView("/medico/detalhes");
                mv.addObject("medico", medico.get());
                mv.addObject("erroExclusao", "Não é possível apagar a conta enquanto existirem registros associados a ela: " + String.join(", ", associados));
                return mv;
            }
            medicoService.delete(medico.get().getId());
        }

        request.logout();
        return new ModelAndView("redirect:/login?contaExcluida=true");
    }
}
