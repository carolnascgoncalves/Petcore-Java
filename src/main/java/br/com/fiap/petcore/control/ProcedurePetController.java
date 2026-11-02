package br.com.fiap.petcore.control;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.petcore.service.procedure.ProcedurePetService;

@RestController
@RequestMapping("/procedure")
public class ProcedurePetController {

    private final ProcedurePetService procedurePetService;

    public ProcedurePetController(ProcedurePetService procedurePetService) {
        this.procedurePetService = procedurePetService;
    }

    @GetMapping("/pets/{idTutor}")
    public String buscarPetsDoTutor(@PathVariable Long idTutor) {
        return procedurePetService.buscarPetsDoTutor(idTutor);
    }
}