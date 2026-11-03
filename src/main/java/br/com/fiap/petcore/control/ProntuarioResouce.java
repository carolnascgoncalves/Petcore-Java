package br.com.fiap.petcore.control;

import java.util.ArrayList;
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

import br.com.fiap.petcore.control.dtos.ProntuarioDadosRequest;
import br.com.fiap.petcore.control.dtos.ProntuarioRequest;
import br.com.fiap.petcore.model.Prontuario;
import br.com.fiap.petcore.model.StatusEnum;
import br.com.fiap.petcore.service.ExameService;
import br.com.fiap.petcore.service.HistoricoService;
import br.com.fiap.petcore.service.MedicoService;
import br.com.fiap.petcore.service.ProntuarioService;
import br.com.fiap.petcore.service.ReceitaService;
import jakarta.validation.Valid;

@Controller
public class ProntuarioResouce {

    @Autowired
    private ProntuarioService prontuarioService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private HistoricoService historicoService;

    @Autowired
    private ExameService exameService;

    @Autowired
    private ReceitaService receitaService;

    @GetMapping("/prontuario/listar")
    public ModelAndView listarProntuarios() {
        ModelAndView mv = new ModelAndView("prontuario/listar");
        mv.addObject("prontuarios", prontuarioService.fetchAll(Pageable.unpaged()).getContent());

        return mv;
    }

    @GetMapping("/prontuario/novo")
    public ModelAndView retornarPaginaCadastro() {
        ModelAndView mv = new ModelAndView("prontuario/novo");
        mv.addObject("prontuario", new ProntuarioRequest());
        mv.addObject("historicosDisponiveis", historicoService.fetchAll(Pageable.unpaged()).getContent().stream().filter(h -> h.getPet() != null && h.getPet().getStatus() == StatusEnum.ATIVO).toList());
        mv.addObject("examesDisponiveis", exameService.fetchAll(Pageable.unpaged()).getContent().stream().filter(exame -> !exame.isRemovido() && exame.getPet() != null && exame.getPet().getStatus() == StatusEnum.ATIVO).toList());
        mv.addObject("receitasDisponiveis", receitaService.fetchAll(Pageable.unpaged()).getContent().stream().filter(receita -> !receita.isRemovida() && receita.getPet() != null && receita.getPet().getStatus() == StatusEnum.ATIVO).toList());

        return mv;
    }

    @PostMapping("/prontuario/cadastrar")
    public ModelAndView cadastrarProntuario(@Valid @ModelAttribute("prontuario") ProntuarioRequest prontuarioRequest,BindingResult bd,Authentication authentication) {
        if (prontuarioRequest.getIdHistorico() != null) {
            var historico = historicoService.fetchById(prontuarioRequest.getIdHistorico());
            if (historico.isEmpty()) {
                bd.rejectValue("idHistorico","historico.invalido","Histórico não encontrado.");
            } else if (historico.get().getPet() != null && historico.get().getPet().getStatus() == StatusEnum.INATIVO) {
                bd.rejectValue("idHistorico","pet.inativo","Não é possível cadastrar um prontuário para um pet inativo.");
            }
        }

        if (prontuarioRequest.getIdExames() != null) {
            for (Long idExame : prontuarioRequest.getIdExames()) {
                if (idExame != null && !exameService.existsById(idExame)) {
                    bd.rejectValue("idExames","exame.invalido","Exame não encontrado.");
                    break;
                }
            }
        }

        if (prontuarioRequest.getIdReceitas() != null) {
            for (Long idReceita : prontuarioRequest.getIdReceitas()) {
                if (idReceita != null && !receitaService.existsById(idReceita)) {
                    bd.rejectValue("idReceitas","receita.invalida","Receita não encontrada.");
                    break;
                }
            }
        }

        var medico = medicoService.fetchByEmail(authentication.getName());
        if (medico.isEmpty()) bd.reject("medico.invalido", "Médico não encontrado.");

        if (bd.hasErrors()) {
            ModelAndView mv = new ModelAndView("prontuario/novo");
            mv.addObject("historicosDisponiveis", historicoService.fetchAll(Pageable.unpaged()).getContent().stream().filter(h -> h.getPet() != null && h.getPet().getStatus() == StatusEnum.ATIVO).toList());
            mv.addObject("examesDisponiveis", exameService.fetchAll(Pageable.unpaged()).getContent().stream().filter(exame -> !exame.isRemovido() && exame.getPet() != null && exame.getPet().getStatus() == StatusEnum.ATIVO).toList());
            mv.addObject("receitasDisponiveis", receitaService.fetchAll(Pageable.unpaged()).getContent().stream().filter(receita -> !receita.isRemovida() && receita.getPet() != null && receita.getPet().getStatus() == StatusEnum.ATIVO).toList());
            return mv;
        }

        Prontuario prontuario = ProntuarioRequest.toEntity(prontuarioRequest);
        prontuario.setMedico(medico.get());
        prontuarioService.create(prontuario);

        return new ModelAndView("redirect:/prontuario/listar");
    }

    @GetMapping("/prontuario/detalhes/{id}")
    public ModelAndView exibirDetalhes(@PathVariable Long id) {
        Optional<Prontuario> op = prontuarioService.fetchById(id);

        if (op.isPresent()) {
            ModelAndView mv = new ModelAndView("prontuario/detalhes");
            mv.addObject("prontuario", op.get());

            return mv;
        }

        return new ModelAndView("redirect:/prontuario/listar");
    }

    @GetMapping("/prontuario/editar/{id}")
    public ModelAndView retornarPaginaEdicao(@PathVariable Long id) {
        Optional<Prontuario> op = prontuarioService.fetchById(id);

        if (op.isPresent()) {
            if (op.get().getHistorico() != null && op.get().getHistorico().getPet() != null && op.get().getHistorico().getPet().getStatus() == StatusEnum.INATIVO) return new ModelAndView("redirect:/prontuario/detalhes/" + id);
            ModelAndView mv = new ModelAndView("prontuario/edicao");
            mv.addObject("prontuario", ProntuarioDadosRequest.toDto(op.get()));
            mv.addObject("id", id);

            return mv;
        }

        return new ModelAndView("redirect:/prontuario/listar");
    }

    @PostMapping("/prontuario/atualizar/{id}")
    public ModelAndView atualizarProntuario(@PathVariable Long id,@Valid @ModelAttribute("prontuario") ProntuarioDadosRequest dadosDto,BindingResult bd) {
        Optional<Prontuario> prontuarioAtual = prontuarioService.fetchById(id);
        if (prontuarioAtual.isEmpty() || (prontuarioAtual.get().getHistorico() != null && prontuarioAtual.get().getHistorico().getPet() != null && prontuarioAtual.get().getHistorico().getPet().getStatus() == StatusEnum.INATIVO)) return new ModelAndView("redirect:/prontuario/listar");
        if (bd.hasErrors()) {
            ModelAndView mv = new ModelAndView("prontuario/edicao");
            mv.addObject("id", id);

            return mv;
        }

        prontuarioService.update(id, ProntuarioDadosRequest.toEntity(dadosDto));

        return new ModelAndView("redirect:/prontuario/listar");
    }

    @GetMapping("/prontuario/remover/{id}")
    public ModelAndView removerProntuario(@PathVariable Long id) {
        Optional<Prontuario> prontuario = prontuarioService.fetchById(id);

        if (prontuario.isPresent()) {
            if (prontuario.get().getHistorico() != null && prontuario.get().getHistorico().getPet() != null && prontuario.get().getHistorico().getPet().getStatus() == StatusEnum.INATIVO) return new ModelAndView("redirect:/prontuario/listar");
            List<String> associados = new ArrayList<>();
            if (prontuario.get().getExames() != null) prontuario.get().getExames().stream().filter(exame -> !exame.isRemovido()).forEach(exame -> associados.add("Exame — " + exame.getNome()));
            if (prontuario.get().getReceitas() != null) prontuario.get().getReceitas().stream().filter(receita -> !receita.isRemovida()).forEach(receita -> associados.add("Receita — " + receita.getNome()));

            if (!associados.isEmpty()) {
                ModelAndView mv = new ModelAndView("prontuario/listar");
                mv.addObject("prontuarios", prontuarioService.fetchAll(Pageable.unpaged()).getContent());
                mv.addObject("erroExclusao", "Não é possível excluir este prontuário, pois existem registros associados a ele: " + String.join(", ", associados));
                return mv;
            }

            prontuarioService.delete(id);
        }

        return new ModelAndView("redirect:/prontuario/listar");
    }
}
