package br.com.fiap.petcore.control;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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

import br.com.fiap.petcore.control.dtos.ReceitaRequest;
import br.com.fiap.petcore.model.Medicamento;
import br.com.fiap.petcore.model.Receita;
import br.com.fiap.petcore.model.StatusEnum;
import br.com.fiap.petcore.service.MedicamentoService;
import br.com.fiap.petcore.service.MedicoService;
import br.com.fiap.petcore.service.ReceitaService;
import br.com.fiap.petcore.service.PetService;
import br.com.fiap.petcore.service.TutorService;
import jakarta.validation.Valid;

@Controller
public class ReceitaResource {

    @Autowired
    private ReceitaService receitaService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private PetService petService;

    @Autowired
    private TutorService tutorService;

    @GetMapping("/receita/listar")
    public ModelAndView listarReceitas(Authentication authentication) {
        ModelAndView mv = new ModelAndView("/receita/listar");
        boolean medico = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MEDICO"));
        if (medico) {
            mv.addObject("receitas", receitaService.fetchAll(Pageable.unpaged()).getContent());
        } else {
            var tutor = tutorService.fetchByEmail(authentication.getName());
            List<Receita> receitas = tutor.isPresent() ? tutor.get().getPets().stream()
                    .flatMap(pet -> pet.getReceitas().stream())
                    .filter(receita -> !receita.isRemovida())
                    .filter(receita -> receita.getValidade() != null && !receita.getValidade().isBefore(LocalDate.now()))
                    .sorted(Comparator.comparing(Receita::getValidade))
                    .toList() : List.of();
            mv.addObject("receitas", receitas);
        }
        mv.addObject("medico", medico);

        return mv;
    }

    @GetMapping("/receita/nova")
    public ModelAndView retornarPaginaCadastro() {
        ModelAndView mv = new ModelAndView("/receita/nova");
        mv.addObject("receita", new ReceitaRequest());
        mv.addObject("medicamentosDisponiveis", medicamentoService.fetchAll(Pageable.unpaged()).getContent());
        mv.addObject("petsAtivos", petService.fetchAll(Pageable.unpaged()).getContent().stream().filter(pet -> pet.getStatus() == StatusEnum.ATIVO).toList());

        return mv;
    }

    @PostMapping("/receita/cadastrar")
    public ModelAndView cadastrarReceita(@Valid @ModelAttribute("receita") ReceitaRequest receitaRequest,BindingResult bd,Authentication authentication) {
        List<Medicamento> medicamentosDisponiveis = medicamentoService.fetchAll(Pageable.unpaged()).getContent();

        if (receitaRequest.getIdPet() != null) {
            var pet = petService.fetchById(receitaRequest.getIdPet());
            if (pet.isEmpty()) {
                bd.rejectValue("idPet","pet.invalido","Pet não encontrado.");
            } else if (pet.get().getStatus() == StatusEnum.INATIVO) {
                bd.rejectValue("idPet","pet.inativo","Não é possível cadastrar uma receita para um pet inativo.");
            }
        }

        if (receitaRequest.getQuantidadeMedicamentos() != null) {
            if (receitaRequest.getQuantidadeMedicamentos() <= 0) {
                bd.rejectValue("quantidadeMedicamentos","quantidade.invalida","A receita deve possuir pelo menos 1 medicamento.");
            } else if (receitaRequest.getQuantidadeMedicamentos() > medicamentosDisponiveis.size()) {
                bd.rejectValue("quantidadeMedicamentos","quantidade.indisponivel","Existem somente " + medicamentosDisponiveis.size() + " medicamentos cadastrados no sistema.");
            }
        }

        if (receitaRequest.getIdMedicamentos() != null) {
            for (Long idMedicamento : receitaRequest.getIdMedicamentos()) {
                if (idMedicamento != null && !medicamentoService.existsById(idMedicamento)) {
                    bd.rejectValue("idMedicamentos","medicamento.invalido","Medicamento não encontrado.");
                    break;
                }
            }

            if (new HashSet<>(receitaRequest.getIdMedicamentos()).size() != receitaRequest.getIdMedicamentos().size()) {
                bd.rejectValue("idMedicamentos","medicamento.duplicado","Não é permitido adicionar o mesmo medicamento mais de uma vez na receita.");
            }
        }

        if (receitaRequest.getQuantidadeMedicamentos() != null && receitaRequest.getQuantidadeMedicamentos() <= medicamentosDisponiveis.size()) {
            int quantidadeSelecionada = receitaRequest.getIdMedicamentos() == null ? 0 : receitaRequest.getIdMedicamentos().size();
            if (quantidadeSelecionada != receitaRequest.getQuantidadeMedicamentos()) {
                bd.rejectValue("idMedicamentos","medicamentos.quantidade","Selecione exatamente " + receitaRequest.getQuantidadeMedicamentos() + " medicamento(s).");
            }
        }

        var medico = medicoService.fetchByEmail(authentication.getName());
        if (medico.isEmpty()) bd.reject("medico.invalido", "Médico não encontrado.");

        if (bd.hasErrors()) {
            ModelAndView mv = new ModelAndView("/receita/nova");
            mv.addObject("medicamentosDisponiveis", medicamentosDisponiveis);
            mv.addObject("petsAtivos", petService.fetchAll(Pageable.unpaged()).getContent().stream().filter(pet -> pet.getStatus() == StatusEnum.ATIVO).toList());

            return mv;
        }

        Receita receita = ReceitaRequest.toEntity(receitaRequest);
        receita.setMedico(medico.get());
        receitaService.create(receita);

        return new ModelAndView("redirect:/receita/listar");
    }

    @GetMapping("/receita/detalhes/{id}")
    public ModelAndView exibirDetalhes(@PathVariable Long id,Authentication authentication) {
        Optional<Receita> op = receitaService.fetchById(id);

        boolean tutor = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TUTOR"));
        if (tutor && op.isPresent()) {
            var tutorLogado = tutorService.fetchByEmail(authentication.getName());
            boolean pertence = tutorLogado.isPresent() && op.get().getPet() != null && tutorLogado.get().getPets().stream().anyMatch(pet -> pet.getId().equals(op.get().getPet().getId()));
            if (!pertence) return new ModelAndView("redirect:/receita/listar");
        }

        if (op.isPresent() && !op.get().isRemovida()) {
            ModelAndView mv = new ModelAndView("/receita/detalhes");
            mv.addObject("receita", op.get());
            mv.addObject("medico", authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MEDICO")));

            return mv;
        }

        return new ModelAndView("redirect:/receita/listar");
    }

    @GetMapping("/receita/remover/{id}")
    public ModelAndView removerReceita(@PathVariable Long id) {
        Optional<Receita> receita = receitaService.fetchById(id);
        if (receita.isPresent() && receita.get().getPet() != null && receita.get().getPet().getStatus() == StatusEnum.INATIVO) return new ModelAndView("redirect:/receita/listar");
        if (receitaService.existsById(id)) receitaService.delete(id);

        return new ModelAndView("redirect:/receita/listar");
    }
}
