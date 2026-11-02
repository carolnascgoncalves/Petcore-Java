package br.com.fiap.petcore.control;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

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

import br.com.fiap.petcore.control.dtos.TutorCreateRequest;
import br.com.fiap.petcore.control.dtos.UserDadosRequest;
import br.com.fiap.petcore.model.StatusEnum;
import br.com.fiap.petcore.model.Tutor;
import br.com.fiap.petcore.service.MedicoService;
import br.com.fiap.petcore.service.TutorService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class TutorResource {

    @Autowired
    private TutorService tutorService;

    @Autowired
    private MedicoService medicoService;

    @GetMapping("/tutor/novo")
    public ModelAndView retornarPaginaCadastro() {
        ModelAndView mv = new ModelAndView("/tutor/novo");
        mv.addObject("tutor", new TutorCreateRequest());
        return mv;
    }

    @PostMapping("/tutor/cadastrar")
    public ModelAndView cadastrarTutor(@Valid @ModelAttribute("tutor") TutorCreateRequest tutorRequest,BindingResult bd) {
        if (tutorRequest.getDataNascimento() != null) {
            LocalDate hoje = LocalDate.now();
            if (!tutorRequest.getDataNascimento().isBefore(hoje)) {
                bd.rejectValue("dataNascimento","dataNascimento.invalida","A data de nascimento não pode ser hoje nem uma data futura.");
            } else if (tutorRequest.getDataNascimento().isAfter(hoje.minusYears(18))) {
                bd.rejectValue("dataNascimento","idade.invalida","O tutor deve ter pelo menos 18 anos.");
            }
        }

        if (tutorRequest.getTelefone() != null && !tutorRequest.getTelefone().isBlank() && (tutorService.existsByTelefone(tutorRequest.getTelefone()) || medicoService.existsByTelefone(tutorRequest.getTelefone()))) {
            bd.rejectValue("telefone","telefone.duplicado","Já existe um usuário cadastrado com este telefone.");
        }

        if (tutorRequest.getEmail() != null && !tutorRequest.getEmail().isBlank() && (tutorService.existsByEmail(tutorRequest.getEmail()) || medicoService.existsByEmail(tutorRequest.getEmail()))) {
            bd.rejectValue("email","email.duplicado","Já existe um usuário cadastrado com este email.");
        }

        if (bd.hasErrors()) return new ModelAndView("/tutor/novo");

        tutorService.create(TutorCreateRequest.toEntity(tutorRequest));
        return new ModelAndView("redirect:/login?cadastro=true");
    }

    @GetMapping("/tutor/minha-conta")
    public ModelAndView minhaConta(Authentication authentication) {
        Optional<Tutor> tutor = tutorService.fetchByEmail(authentication.getName());
        if (tutor.isPresent()) {
            ModelAndView mv = new ModelAndView("/tutor/detalhes");
            mv.addObject("tutor", tutor.get());
            return mv;
        }
        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/tutor/editar")
    public ModelAndView retornarPaginaEdicao(Authentication authentication) {
        Optional<Tutor> tutor = tutorService.fetchByEmail(authentication.getName());
        if (tutor.isPresent()) {
            ModelAndView mv = new ModelAndView("/tutor/edicao");
            UserDadosRequest dto = UserDadosRequest.toDto(tutor.get());
            dto.setSenha(null);
            mv.addObject("tutor", dto);
            return mv;
        }
        return new ModelAndView("redirect:/home");
    }

    @PostMapping("/tutor/atualizar")
    public ModelAndView atualizarTutor(@Valid @ModelAttribute("tutor") UserDadosRequest dadosDto,BindingResult bd,Authentication authentication) {
        Optional<Tutor> atual = tutorService.fetchByEmail(authentication.getName());
        if (atual.isEmpty()) return new ModelAndView("redirect:/home");

        if (dadosDto.getTelefone() != null && !dadosDto.getTelefone().isBlank() && ((!dadosDto.getTelefone().equals(atual.get().getTelefone()) && tutorService.existsByTelefone(dadosDto.getTelefone())) || medicoService.existsByTelefone(dadosDto.getTelefone()))) {
            bd.rejectValue("telefone","telefone.duplicado","Já existe um usuário cadastrado com este telefone.");
        }
        if (dadosDto.getEmail() != null && !dadosDto.getEmail().isBlank() && ((!dadosDto.getEmail().equals(atual.get().getEmail()) && tutorService.existsByEmail(dadosDto.getEmail())) || medicoService.existsByEmail(dadosDto.getEmail()))) {
            bd.rejectValue("email","email.duplicado","Já existe um usuário cadastrado com este email.");
        }

        if (bd.hasErrors()) return new ModelAndView("/tutor/edicao");

        Optional<Tutor> atualizado = tutorService.update(atual.get().getId(), UserDadosRequest.toEntity(dadosDto));
        if (atualizado.isPresent()) {
            UsernamePasswordAuthenticationToken novaAutenticacao = new UsernamePasswordAuthenticationToken(atualizado.get().getEmail(), authentication.getCredentials(), authentication.getAuthorities());
            novaAutenticacao.setDetails(authentication.getDetails());
            SecurityContextHolder.getContext().setAuthentication(novaAutenticacao);
        }
        return new ModelAndView("redirect:/tutor/minha-conta");
    }

    @GetMapping("/tutor/apagar")
    public ModelAndView apagarConta(Authentication authentication,HttpServletRequest request) throws ServletException {
        Optional<Tutor> tutor = tutorService.fetchByEmail(authentication.getName());
        if (tutor.isPresent() && tutor.get().getPets() != null) {
            var petsAtivos = tutor.get().getPets().stream().filter(pet -> pet.getStatus() == StatusEnum.ATIVO).toList();
            if (!petsAtivos.isEmpty()) {
                ModelAndView mv = new ModelAndView("/tutor/detalhes");
                mv.addObject("tutor", tutor.get());
                mv.addObject("erroExclusao", "Não é possível apagar a conta enquanto houver pets ativos. Inative primeiro: " + petsAtivos.stream().map(pet -> pet.getNome()).collect(Collectors.joining(", ")));
                return mv;
            }
        }

        if (tutor.isPresent()) tutorService.delete(tutor.get().getId());
        request.logout();
        return new ModelAndView("redirect:/login?contaExcluida=true");
    }
}
