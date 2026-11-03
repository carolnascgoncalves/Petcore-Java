package br.com.fiap.petcore.control;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.petcore.model.Exame;
import br.com.fiap.petcore.model.Historico;
import br.com.fiap.petcore.model.Receita;
import br.com.fiap.petcore.service.HistoricoService;
import br.com.fiap.petcore.service.TutorService;

@Controller
public class HistoricoResource {

    @Autowired
    private HistoricoService historicoService;

    @Autowired
    private TutorService tutorService;
    
    @GetMapping("/historico/listar")
    public ModelAndView listarHistoricos(Authentication authentication) {
        ModelAndView mv = new ModelAndView("historico/listar");
        boolean medico = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MEDICO"));
        if (medico) {
            mv.addObject("historicos", historicoService.fetchAll(Pageable.unpaged()).getContent());
        } else {
            var tutor = tutorService.fetchByEmail(authentication.getName());
            List<Historico> historicos = tutor.isPresent() ? tutor.get().getPets().stream().map(pet -> pet.getHistorico()).filter(historico -> historico != null).toList() : new ArrayList<>();
            mv.addObject("historicos", historicos);
        }
        mv.addObject("medico", medico);

        return mv;
    }

    @GetMapping("/historico/detalhes/{id}")
    public ModelAndView exibirDetalhes(@PathVariable Long id,Authentication authentication) {
        Optional<Historico> op = historicoService.fetchById(id);

        boolean tutor = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TUTOR"));
        if (tutor && op.isPresent()) {
            var tutorLogado = tutorService.fetchByEmail(authentication.getName());
            boolean pertence = tutorLogado.isPresent() && tutorLogado.get().getPets().stream().anyMatch(pet -> pet.getHistorico() != null && pet.getHistorico().getId().equals(id));
            if (!pertence) return new ModelAndView("redirect:/historico/listar");
        }

        if (op.isPresent()) {
            ModelAndView mv = new ModelAndView("historico/detalhes");
            Historico historico = op.get();
            LocalDateTime agora = LocalDateTime.now();
            List<Exame> examesRealizados = new ArrayList<>();
            List<Receita> receitasVencidas = new ArrayList<>();

            if (historico.getPet() != null) {
                if (historico.getPet().getExames() != null) {
                    examesRealizados = historico.getPet().getExames().stream()
                            .filter(exame -> exame.getData() != null && !exame.getData().isAfter(agora))
                            .sorted(Comparator.comparing(Exame::getData).reversed())
                            .toList();
                }

                if (historico.getPet().getReceitas() != null) {
                    receitasVencidas = historico.getPet().getReceitas().stream()
                            .filter(receita -> receita.getValidade() != null && receita.getValidade().isBefore(LocalDate.now()))
                            .sorted(Comparator.comparing(Receita::getValidade).reversed())
                            .toList();
                }
            }

            mv.addObject("historico", historico);
            mv.addObject("examesRealizados", examesRealizados);
            mv.addObject("receitasVencidas", receitasVencidas);
            mv.addObject("tutor", tutor);

            return mv;
        }

        return new ModelAndView("redirect:/historico/listar");
    }


    @GetMapping("/historico/remover/{id}")
    public ModelAndView removerHistorico(@PathVariable Long id) {
        Optional<Historico> historico = historicoService.fetchById(id);

        if (historico.isPresent()) {
            List<String> associados = new ArrayList<>();
            if (historico.get().getPet() != null) associados.add("Pet " + historico.get().getPet().getNome());
            if (historico.get().getProntuarios() != null) historico.get().getProntuarios().forEach(prontuario -> associados.add("Prontuário do pet " + (historico.get().getPet() != null ? historico.get().getPet().getNome() : "sem pet")));
            if (historico.get().getRelatorios() != null) historico.get().getRelatorios().forEach(relatorio -> associados.add("Relatório do pet " + (historico.get().getPet() != null ? historico.get().getPet().getNome() : "sem pet")));

            if (!associados.isEmpty()) {
                ModelAndView mv = new ModelAndView("historico/listar");
                mv.addObject("historicos", historicoService.fetchAll(Pageable.unpaged()).getContent());
                mv.addObject("erroExclusao", "Não é possível excluir este histórico, pois existem registros associados a ele: " + String.join(", ", associados));
                return mv;
            }

            historicoService.delete(id);
        }

        return new ModelAndView("redirect:/historico/listar");
    }
}
