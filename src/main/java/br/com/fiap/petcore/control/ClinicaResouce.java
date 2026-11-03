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

import br.com.fiap.petcore.control.dtos.ClinicaDadosRequest;
import br.com.fiap.petcore.control.dtos.ClinicaRequest;
import br.com.fiap.petcore.model.Clinica;
import br.com.fiap.petcore.service.ClinicaService;
import jakarta.validation.Valid;

@Controller
public class ClinicaResouce {

    @Autowired
    private ClinicaService clinicaService;

    @GetMapping("/clinica/listar")
    public ModelAndView listarClinicas() {
        ModelAndView mv = new ModelAndView("clinica/listar");
        mv.addObject("clinicas", clinicaService.fetchAll(Pageable.unpaged()).getContent());

        return mv;
    }

    @GetMapping("/clinica/nova")
    public ModelAndView retornarPaginaCadastro() {
        ModelAndView mv = new ModelAndView("clinica/nova");
        mv.addObject("clinica", new ClinicaRequest());

        return mv;
    }

    @PostMapping("/clinica/cadastrar")
    public ModelAndView cadastrar(@Valid @ModelAttribute("clinica") ClinicaRequest clinica,BindingResult bd) {

        if (bd.hasErrors()) {
            return new ModelAndView("clinica/nova");
        }

        Clinica novaClinica = ClinicaRequest.toEntity(clinica);
        clinicaService.create(novaClinica);

        return new ModelAndView("redirect:/clinica/listar");
    }

    @GetMapping("/clinica/detalhes/{id}")
    public ModelAndView exibirDetalhes(@PathVariable Long id) {
        Optional<Clinica> op = clinicaService.fetchById(id);

        if (op.isPresent()) {
            ModelAndView mv = new ModelAndView("clinica/detalhes");
            mv.addObject("clinica", op.get());

            return mv;
        }

        return new ModelAndView("redirect:/clinica/listar");
    }

    @GetMapping("/clinica/editar/{id}")
    public ModelAndView retornarPaginaEdicao(@PathVariable Long id) {
        Optional<Clinica> op = clinicaService.fetchById(id);

        if (op.isPresent()) {
            ModelAndView mv = new ModelAndView("clinica/edicao");
            mv.addObject("clinica", ClinicaDadosRequest.toDto(op.get()));
            mv.addObject("id", id);

            return mv;
        }

        return new ModelAndView("redirect:/clinica/listar");
    }

    @PostMapping("/clinica/atualizar/{id}")
    public ModelAndView atualizarClinica(@PathVariable Long id,@Valid @ModelAttribute("clinica") ClinicaDadosRequest dadosDto,BindingResult bd) {

        if (bd.hasErrors()) {
            ModelAndView mv = new ModelAndView("clinica/edicao");
            mv.addObject("id", id);

            return mv;
        }

        clinicaService.update(id, ClinicaDadosRequest.toEntity(dadosDto));

        return new ModelAndView("redirect:/clinica/listar");
    }

    @GetMapping("/clinica/remover/{id}")
    public ModelAndView removerClinica(@PathVariable Long id) {
        Optional<Clinica> clinica = clinicaService.fetchById(id);

        if (clinica.isPresent() && clinica.get().getRelatorios() != null && !clinica.get().getRelatorios().isEmpty()) {
            ModelAndView mv = new ModelAndView("clinica/listar");
            mv.addObject("clinicas", clinicaService.fetchAll(Pageable.unpaged()).getContent());
            mv.addObject("erroExclusao", "Não é possível excluir esta clínica, pois ela está associada aos seguintes relatórios: " + clinica.get().getRelatorios().stream().map(relatorio -> "Relatório " + relatorio.getId()).collect(Collectors.joining(", ")));
            return mv;
        }

        if (clinica.isPresent()) clinicaService.delete(id);
        return new ModelAndView("redirect:/clinica/listar");
    }
}
