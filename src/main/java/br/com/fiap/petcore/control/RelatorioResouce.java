package br.com.fiap.petcore.control;

import java.util.Optional;
import java.util.stream.Collectors;

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

import br.com.fiap.petcore.control.dtos.RelatorioDadosRequest;
import br.com.fiap.petcore.control.dtos.RelatorioRequest;
import br.com.fiap.petcore.model.Relatorio;
import br.com.fiap.petcore.model.StatusEnum;
import br.com.fiap.petcore.service.HistoricoService;
import br.com.fiap.petcore.service.MedicoService;
import br.com.fiap.petcore.service.RelatorioService;
import jakarta.validation.Valid;

@Controller
public class RelatorioResouce {

    @Autowired
    private RelatorioService relatorioService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private HistoricoService historicoService;

    @GetMapping("/relatorio/listar")
    public ModelAndView listarRelatorios() {
        ModelAndView mv = new ModelAndView("/relatorio/listar");
        mv.addObject("relatorios", relatorioService.fetchAll(Pageable.unpaged()).getContent());

        return mv;
    }

    @GetMapping("/relatorio/novo")
    public ModelAndView retornarPaginaCadastro() {
        ModelAndView mv = new ModelAndView("/relatorio/novo");
        mv.addObject("relatorio", new RelatorioRequest());
        mv.addObject("historicosDisponiveis", historicoService.fetchAll(Pageable.unpaged()).getContent().stream().filter(h -> h.getPet() != null && h.getPet().getStatus() == StatusEnum.ATIVO).toList());

        return mv;
    }

    @PostMapping("/relatorio/cadastrar")
    public ModelAndView cadastrarRelatorio(@Valid @ModelAttribute("relatorio") RelatorioRequest relatorioRequest,BindingResult bd,Authentication authentication) {
        if (relatorioRequest.getIdHist() != null) {
            var historico = historicoService.fetchById(relatorioRequest.getIdHist());
            if (historico.isEmpty()) {
                bd.rejectValue("idHist","historico.invalido","Histórico não encontrado.");
            } else if (historico.get().getPet() != null && historico.get().getPet().getStatus() == StatusEnum.INATIVO) {
                bd.rejectValue("idHist","pet.inativo","Não é possível cadastrar um relatório para um pet inativo.");
            }
        }

        var medico = medicoService.fetchByEmail(authentication.getName());
        if (medico.isEmpty()) bd.reject("medico.invalido", "Médico não encontrado.");

        if (bd.hasErrors()) {
            ModelAndView mv = new ModelAndView("/relatorio/novo");
            mv.addObject("historicosDisponiveis", historicoService.fetchAll(Pageable.unpaged()).getContent().stream().filter(h -> h.getPet() != null && h.getPet().getStatus() == StatusEnum.ATIVO).toList());
            return mv;
        }

        Relatorio relatorio = RelatorioRequest.toEntity(relatorioRequest);
        relatorio.setMedico(medico.get());
        relatorioService.create(relatorio);

        return new ModelAndView("redirect:/relatorio/listar");
    }

    @GetMapping("/relatorio/detalhes/{id}")
    public ModelAndView exibirDetalhes(@PathVariable Long id) {
        Optional<Relatorio> op = relatorioService.fetchById(id);

        if (op.isPresent()) {
            ModelAndView mv = new ModelAndView("/relatorio/detalhes");
            mv.addObject("relatorio", op.get());

            return mv;
        }

        return new ModelAndView("redirect:/relatorio/listar");
    }

    @GetMapping("/relatorio/editar/{id}")
    public ModelAndView retornarPaginaEdicao(@PathVariable Long id) {
        Optional<Relatorio> op = relatorioService.fetchById(id);

        if (op.isPresent()) {
            if (op.get().getHistorico() != null && op.get().getHistorico().getPet() != null && op.get().getHistorico().getPet().getStatus() == StatusEnum.INATIVO) return new ModelAndView("redirect:/relatorio/detalhes/" + id);
            ModelAndView mv = new ModelAndView("/relatorio/edicao");
            mv.addObject("relatorio", RelatorioDadosRequest.toDto(op.get()));
            mv.addObject("id", id);

            return mv;
        }

        return new ModelAndView("redirect:/relatorio/listar");
    }

    @PostMapping("/relatorio/atualizar/{id}")
    public ModelAndView atualizarRelatorio(@PathVariable Long id,@Valid @ModelAttribute("relatorio") RelatorioDadosRequest dadosDto,BindingResult bd) {
        Optional<Relatorio> relatorioAtual = relatorioService.fetchById(id);
        if (relatorioAtual.isEmpty() || (relatorioAtual.get().getHistorico() != null && relatorioAtual.get().getHistorico().getPet() != null && relatorioAtual.get().getHistorico().getPet().getStatus() == StatusEnum.INATIVO)) return new ModelAndView("redirect:/relatorio/listar");
        if (bd.hasErrors()) {
            ModelAndView mv = new ModelAndView("/relatorio/edicao");
            mv.addObject("id", id);

            return mv;
        }

        relatorioService.update(id, RelatorioDadosRequest.toEntity(dadosDto));

        return new ModelAndView("redirect:/relatorio/listar");
    }

    @GetMapping("/relatorio/remover/{id}")
    public ModelAndView removerRelatorio(@PathVariable Long id) {
        Optional<Relatorio> relatorio = relatorioService.fetchById(id);

        if (relatorio.isPresent() && relatorio.get().getHistorico() != null && relatorio.get().getHistorico().getPet() != null && relatorio.get().getHistorico().getPet().getStatus() == StatusEnum.INATIVO) return new ModelAndView("redirect:/relatorio/listar");

        if (relatorio.isPresent() && relatorio.get().getClinicas() != null && !relatorio.get().getClinicas().isEmpty()) {
            ModelAndView mv = new ModelAndView("/relatorio/listar");
            mv.addObject("relatorios", relatorioService.fetchAll(Pageable.unpaged()).getContent());
            mv.addObject("erroExclusao", "Não é possível excluir este relatório, pois ele está associado às seguintes clínicas: " + relatorio.get().getClinicas().stream().map(clinica -> "Clínica — " + clinica.getNome()).collect(Collectors.joining(", ")));
            return mv;
        }

        if (relatorio.isPresent()) relatorioService.delete(id);
        return new ModelAndView("redirect:/relatorio/listar");
    }
}
