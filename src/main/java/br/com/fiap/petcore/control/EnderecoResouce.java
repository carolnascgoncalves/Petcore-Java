package br.com.fiap.petcore.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.petcore.control.dtos.EnderecoRequest;
import br.com.fiap.petcore.model.Endereco;
import br.com.fiap.petcore.service.EnderecoService;
import jakarta.validation.Valid;

@Controller
public class EnderecoResouce {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/endereco/listar")
    public ModelAndView listarEnderecos() {
        ModelAndView mv = new ModelAndView("/endereco/listar");
        mv.addObject("enderecos", enderecoService.fetchAll(Pageable.unpaged()).getContent());

        return mv;
    }

    @GetMapping("/endereco/novo")
    public ModelAndView retornarPaginaCadastro() {
        ModelAndView mv = new ModelAndView("/endereco/novo");
        mv.addObject("endereco", new EnderecoRequest());

        return mv;
    }

    @PostMapping("/endereco/cadastrar")
    public ModelAndView cadastrarEndereco(@Valid @ModelAttribute("endereco") EnderecoRequest enderecoRequest,BindingResult bd) {
        if (bd.hasErrors()) {
            return new ModelAndView("/endereco/novo");
        }

        Endereco endereco = EnderecoRequest.toEntity(enderecoRequest);
        enderecoService.create(endereco);

        return new ModelAndView("redirect:/endereco/listar");
    }

    @GetMapping("/endereco/detalhes/{id}")
    public ModelAndView exibirDetalhes(@PathVariable Long id) {
        Optional<Endereco> op = enderecoService.fetchById(id);

        if (op.isPresent()) {
            ModelAndView mv = new ModelAndView("/endereco/detalhes");
            mv.addObject("endereco", op.get());

            return mv;
        }

        return new ModelAndView("redirect:/endereco/listar");
    }

    @GetMapping("/endereco/editar/{id}")
    public ModelAndView retornarPaginaEdicao(@PathVariable Long id) {
        Optional<Endereco> op = enderecoService.fetchById(id);

        if (op.isPresent()) {
            ModelAndView mv = new ModelAndView("/endereco/edicao");
            mv.addObject("endereco", EnderecoRequest.toDto(op.get()));
            mv.addObject("id", id);

            return mv;
        }

        return new ModelAndView("redirect:/endereco/listar");
    }

    @PostMapping("/endereco/atualizar/{id}")
    public ModelAndView atualizarEndereco(@PathVariable Long id,@Valid @ModelAttribute("endereco") EnderecoRequest enderecoRequest,BindingResult bd) {
        if (bd.hasErrors()) {
            ModelAndView mv = new ModelAndView("/endereco/edicao");
            mv.addObject("id", id);

            return mv;
        }

        enderecoService.update(id, EnderecoRequest.toEntity(enderecoRequest));

        return new ModelAndView("redirect:/endereco/listar");
    }

    @GetMapping("/endereco/remover/{id}")
    public ModelAndView removerEndereco(@PathVariable Long id) {
        Optional<Endereco> endereco = enderecoService.fetchById(id);

        if (endereco.isPresent() && endereco.get().getClinica() != null) {
            ModelAndView mv = new ModelAndView("/endereco/listar");
            mv.addObject("enderecos", enderecoService.fetchAll(Pageable.unpaged()).getContent());
            mv.addObject("erroExclusao", "Não é possível excluir este endereço, pois ele está associado à clínica " + endereco.get().getClinica().getNome());
            return mv;
        }

        if (endereco.isPresent()) enderecoService.delete(id);
        return new ModelAndView("redirect:/endereco/listar");
    }
}
