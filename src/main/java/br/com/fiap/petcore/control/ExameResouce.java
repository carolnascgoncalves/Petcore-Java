package br.com.fiap.petcore.control;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.petcore.control.dtos.ExameDadosRequest;
import br.com.fiap.petcore.control.dtos.ExameRequest;
import br.com.fiap.petcore.model.Exame;
import br.com.fiap.petcore.service.ExameService;
import br.com.fiap.petcore.model.StatusEnum;
import br.com.fiap.petcore.service.MedicoService;
import br.com.fiap.petcore.service.PetService;
import br.com.fiap.petcore.service.TutorService;
import jakarta.validation.Valid;

@Controller
public class ExameResouce {

    @Autowired
    private ExameService exameService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PetService petService;

    @Autowired
    private TutorService tutorService;

    @GetMapping("/exame/listar")
    public ModelAndView listarExames(Authentication authentication) {
        ModelAndView mv = new ModelAndView("/exame/listar");
        boolean medico = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MEDICO"));
        if (medico) {
            mv.addObject("exames", exameService.fetchAll(Pageable.unpaged()).getContent());
        } else {
            var tutor = tutorService.fetchByEmail(authentication.getName());
            List<Exame> exames = tutor.isPresent() ? tutor.get().getPets().stream()
                    .flatMap(pet -> pet.getExames().stream())
                    .filter(exame -> !exame.isRemovido())
                    .filter(exame -> exame.getData() != null && !exame.getData().isBefore(LocalDateTime.now()))
                    .sorted(Comparator.comparing(Exame::getData))
                    .toList() : List.of();
            mv.addObject("exames", exames);
        }
        mv.addObject("medico", medico);

        return mv;
    }

    @GetMapping("/exame/novo")
    public ModelAndView retornarPaginaCadastro() {
        ModelAndView mv = new ModelAndView("/exame/novo");
        mv.addObject("exame", new ExameRequest());
        mv.addObject("petsAtivos", petService.fetchAll(Pageable.unpaged()).getContent().stream().filter(pet -> pet.getStatus() == StatusEnum.ATIVO).toList());

        return mv;
    }

    @PostMapping("/exame/cadastrar")
    public ModelAndView cadastrarExame(@Valid @ModelAttribute("exame") ExameRequest exameRequest,BindingResult bd,Authentication authentication) {
        if (exameRequest.getIdPet() != null) {
            var pet = petService.fetchById(exameRequest.getIdPet());
            if (pet.isEmpty()) {
                bd.rejectValue("idPet","pet.invalido","Pet não encontrado.");
            } else if (pet.get().getStatus() == StatusEnum.INATIVO) {
                bd.rejectValue("idPet","pet.inativo","Não é possível cadastrar um exame para um pet inativo.");
            }
        }

        var medico = medicoService.fetchByEmail(authentication.getName());
        if (medico.isEmpty()) bd.reject("medico.invalido", "Médico não encontrado.");

        if (bd.hasErrors()) {
            ModelAndView mv = new ModelAndView("/exame/novo");
            mv.addObject("petsAtivos", petService.fetchAll(Pageable.unpaged()).getContent().stream().filter(pet -> pet.getStatus() == StatusEnum.ATIVO).toList());
            return mv;
        }

        Exame exame = ExameRequest.toEntity(exameRequest);
        exame.setMedico(medico.get());
        exameService.create(exame);

        return new ModelAndView("redirect:/exame/listar");
    }

    @GetMapping("/exame/detalhes/{id}")
    public ModelAndView exibirDetalhes(@PathVariable Long id,Authentication authentication) {
        Optional<Exame> op = exameService.fetchById(id);

        boolean tutor = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TUTOR"));
        if (tutor && op.isPresent()) {
            var tutorLogado = tutorService.fetchByEmail(authentication.getName());
            boolean pertence = tutorLogado.isPresent() && op.get().getPet() != null && tutorLogado.get().getPets().stream().anyMatch(pet -> pet.getId().equals(op.get().getPet().getId()));
            if (!pertence) return new ModelAndView("redirect:/exame/listar");
        }

        if (op.isPresent()) {
            ModelAndView mv = new ModelAndView("/exame/detalhes");
            mv.addObject("exame", op.get());
            mv.addObject("medico", authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MEDICO")));

            return mv;
        }

        return new ModelAndView("redirect:/exame/listar");
    }

    @GetMapping("/exame/editar/{id}")
    public ModelAndView retornarPaginaEdicao(@PathVariable Long id) {
        Optional<Exame> op = exameService.fetchById(id);

        if (op.isPresent()) {
            if (op.get().getPet() != null && op.get().getPet().getStatus() == StatusEnum.INATIVO) return new ModelAndView("redirect:/exame/detalhes/" + id);
            ModelAndView mv = new ModelAndView("/exame/edicao");
            mv.addObject("exame", ExameDadosRequest.toDto(op.get()));
            mv.addObject("id", id);

            return mv;
        }

        return new ModelAndView("redirect:/exame/listar");
    }

    @PostMapping("/exame/atualizar/{id}")
    public ModelAndView atualizarExame(@PathVariable Long id,@Valid @ModelAttribute("exame") ExameDadosRequest dadosDto,BindingResult bd) {
        Optional<Exame> exameAtual = exameService.fetchById(id);
        if (exameAtual.isEmpty() || (exameAtual.get().getPet() != null && exameAtual.get().getPet().getStatus() == StatusEnum.INATIVO)) return new ModelAndView("redirect:/exame/listar");
        if (dadosDto.getData() != null && dadosDto.getData().toLocalDate().isBefore(LocalDate.now())) {
            bd.rejectValue("data","data.invalida","A data não pode ser anterior à data atual.");
        }

        if (bd.hasErrors()) {
            ModelAndView mv = new ModelAndView("/exame/edicao");
            mv.addObject("id", id);

            return mv;
        }

        exameService.update(id, ExameDadosRequest.toEntity(dadosDto));

        return new ModelAndView("redirect:/exame/listar");
    }

    @GetMapping("/exame/remover/{id}")
    public ModelAndView removerExame(@PathVariable Long id) {
        Optional<Exame> exame = exameService.fetchById(id);
        if (exame.isPresent() && exame.get().getPet() != null && exame.get().getPet().getStatus() == StatusEnum.INATIVO) return new ModelAndView("redirect:/exame/listar");
        if (exameService.existsById(id)) exameService.delete(id);

        return new ModelAndView("redirect:/exame/listar");
    }
}
