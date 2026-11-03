package br.com.fiap.petcore.control;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.petcore.control.dtos.MedicamentoDadosRequest;
import br.com.fiap.petcore.control.dtos.MedicamentoRequest;
import br.com.fiap.petcore.model.Medicamento;
import br.com.fiap.petcore.service.MedicamentoService;
import jakarta.validation.Valid;

@Controller
public class MedicamentoResource {

    @Autowired
    private MedicamentoService medicamentoService;

    @GetMapping("/medicamento/listar")
    public ModelAndView listarMedicamentos() {
        ModelAndView mv = new ModelAndView("medicamento/listar");
        mv.addObject("medicamentos", medicamentoService.fetchAll(Pageable.unpaged()).getContent());

        return mv;
    }

    @GetMapping("/medicamento/novo")
    public ModelAndView retornarPaginaCadastro() {
        ModelAndView mv = new ModelAndView("medicamento/novo");
        mv.addObject("medicamento", new MedicamentoRequest());

        return mv;
    }

    @PostMapping("/medicamento/cadastrar")
    public ModelAndView cadastrarMedicamento(@Valid @ModelAttribute("medicamento") MedicamentoRequest medicamentoRequest,BindingResult bd) {
        if (bd.hasErrors()) {
            return new ModelAndView("medicamento/novo");
        }

        Medicamento medicamento = MedicamentoRequest.toEntity(medicamentoRequest);
        medicamentoService.create(medicamento);

        return new ModelAndView("redirect:/medicamento/listar");
    }

    @GetMapping("/medicamento/detalhes/{id}")
    public ModelAndView exibirDetalhes(@PathVariable Long id) {
        Optional<Medicamento> op = medicamentoService.fetchById(id);

        if (op.isPresent()) {
            ModelAndView mv = new ModelAndView("medicamento/detalhes");
            mv.addObject("medicamento", op.get());

            return mv;
        }

        return new ModelAndView("redirect:/medicamento/listar");
    }

    @GetMapping("/medicamento/editar/{id}")
    public ModelAndView retornarPaginaEdicao(@PathVariable Long id) {
        Optional<Medicamento> op = medicamentoService.fetchById(id);

        if (op.isPresent()) {
            ModelAndView mv = new ModelAndView("medicamento/edicao");
            mv.addObject("medicamento", MedicamentoDadosRequest.toDto(op.get()));
            mv.addObject("id", id);

            return mv;
        }

        return new ModelAndView("redirect:/medicamento/listar");
    }

    @PostMapping("/medicamento/atualizar/{id}")
    public ModelAndView atualizarMedicamento(@PathVariable Long id,@Valid @ModelAttribute("medicamento") MedicamentoDadosRequest dadosDto,BindingResult bd) {
        if (bd.hasErrors()) {
            ModelAndView mv = new ModelAndView("medicamento/edicao");
            mv.addObject("id", id);

            return mv;
        }

        medicamentoService.update(id, MedicamentoDadosRequest.toEntity(dadosDto));

        return new ModelAndView("redirect:/medicamento/listar");
    }

    @GetMapping("/medicamento/remover/{id}")
    public ModelAndView removerMedicamento(@PathVariable Long id) {
        Optional<Medicamento> medicamento = medicamentoService.fetchById(id);

        if (medicamento.isPresent() && medicamento.get().getReceitas() != null && !medicamento.get().getReceitas().isEmpty()) {
            ModelAndView mv = new ModelAndView("medicamento/listar");
            mv.addObject("medicamentos", medicamentoService.fetchAll(Pageable.unpaged()).getContent());
            mv.addObject("erroExclusao", "Não é possível excluir este medicamento, pois ele está associado às seguintes receitas: " + medicamento.get().getReceitas().stream().map(receita -> "Receita — " + receita.getNome()).collect(Collectors.joining(", ")));
            return mv;
        }

        if (medicamento.isPresent()) medicamentoService.delete(id);
        return new ModelAndView("redirect:/medicamento/listar");
    }
}
