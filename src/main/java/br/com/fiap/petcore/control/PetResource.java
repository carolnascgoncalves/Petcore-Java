package br.com.fiap.petcore.control;

import java.util.ArrayList;import java.io.IOException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import br.com.fiap.petcore.service.ImagemPetService;
import java.util.HashSet;
import java.util.Set;
import br.com.fiap.petcore.service.procedure.ProcedurePetService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
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

import br.com.fiap.petcore.control.dtos.PetDadosRequest;
import br.com.fiap.petcore.control.dtos.PetImgRequest;
import br.com.fiap.petcore.control.dtos.PetRequest;
import br.com.fiap.petcore.model.Pet;
import br.com.fiap.petcore.model.StatusEnum;
import br.com.fiap.petcore.service.PetService;
import br.com.fiap.petcore.service.TutorService;
import jakarta.validation.Valid;

@Controller
public class PetResource {

    @Autowired
    private PetService petService;
    
    @Autowired
    private ProcedurePetService procedurePetService;

    @Autowired
    private TutorService tutorService;
    
    @Autowired
    private ImagemPetService imagemPetService;


@GetMapping("/pet/listar")
public ModelAndView listarPets(Authentication authentication) {
    ModelAndView mv = new ModelAndView("/pet/listar");

    boolean tutor = authentication.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_TUTOR"));

    if (tutor) {
        var tutorLogado = tutorService.fetchByEmail(authentication.getName());

        if (tutorLogado.isPresent()) {

            // Executa a procedure utilizando o ID do tutor autenticado
            List<Long> idsPets = procedurePetService.buscarIdsPetsDoTutor(
                    tutorLogado.get().getId());

            // Mantém somente os pets retornados pela procedure
            Set<Long> idsEncontrados = new HashSet<>(idsPets);

            List<Pet> pets = tutorLogado.get().getPets().stream()
                    .filter(pet -> idsEncontrados.contains(pet.getId()))
                    .sorted(Comparator.comparing(Pet::getId))
                    .toList();

            mv.addObject("pets", pets);

        } else {
            mv.addObject("pets", new ArrayList<>());
        }

    } else {
        // Médicos continuam visualizando todos os pets
        mv.addObject("pets", petService.fetchAll(Pageable.unpaged()).getContent());
    }

    mv.addObject("tutor", tutor);

    return mv;
}


    @GetMapping("/pet/novo")
    public ModelAndView retornarPaginaCadastro() {
        ModelAndView mv = new ModelAndView("/pet/novo");
        mv.addObject("pet", new PetRequest());

        return mv;
    }

    @PostMapping("/pet/cadastrar")
    public ModelAndView cadastrarPet(@Valid @ModelAttribute("pet") PetRequest petRequest,BindingResult bd, @RequestParam(value = "imagem", required = false) MultipartFile imagem,Authentication authentication) {
        var tutor = tutorService.fetchByEmail(authentication.getName());

        if (tutor.isEmpty()) {
            bd.reject("tutor.invalido", "O pet deve ser cadastrado por uma conta de tutor.");
        }

        if (bd.hasErrors()) {
            return new ModelAndView("/pet/novo");
        }

        Pet pet = PetRequest.toEntity(petRequest);

        try {
            if (imagem != null && !imagem.isEmpty()) {
                pet.setUrlImg(imagemPetService.salvar(imagem));
            }
        } catch (IOException | IllegalArgumentException e) {
            bd.reject("imagem.invalida", e.getMessage());
            return new ModelAndView("/pet/novo");
        }

        Pet petSalvo = petService.create(pet);
        tutorService.associatePet(tutor.get().getId(), petSalvo);

        return new ModelAndView("redirect:/pet/listar");
    }

    @GetMapping("/pet/detalhes/{id}")
    public ModelAndView exibirDetalhes(@PathVariable Long id,Authentication authentication) {
        Optional<Pet> op = petService.fetchById(id);

        boolean tutor = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TUTOR"));
        if (tutor && !petPertenceAoTutor(id, authentication)) return new ModelAndView("redirect:/pet/listar");

        if (op.isPresent()) {
            Pet pet = op.get();
            ModelAndView mv = new ModelAndView("/pet/detalhes");
            mv.addObject("pet", pet);
            mv.addObject("tutor", tutor);
            mv.addObject("examesAgendados", pet.getExames() == null ? List.of() : pet.getExames().stream()
                    .filter(exame -> !exame.isRemovido() && exame.getData() != null && exame.getData().isAfter(LocalDateTime.now()))
                    .sorted(Comparator.comparing(exame -> exame.getData()))
                    .toList());
            mv.addObject("receitasAtivas", pet.getReceitas() == null ? List.of() : pet.getReceitas().stream()
                    .filter(receita -> !receita.isRemovida() && receita.getValidade() != null && !receita.getValidade().isBefore(LocalDate.now()))
                    .sorted(Comparator.comparing(receita -> receita.getValidade()))
                    .toList());

            return mv;
        }

        return new ModelAndView("redirect:/pet/listar");
    }

    @GetMapping("/pet/editar/status/{id}")
    public ModelAndView retornarPaginaEdicaoStatus(@PathVariable Long id,Authentication authentication) {
        if (!petPertenceAoTutor(id, authentication)) return new ModelAndView("redirect:/pet/listar");
        Optional<Pet> op = petService.fetchById(id);

        if (op.isPresent()) {
            if (op.get().getStatus() == StatusEnum.INATIVO) return new ModelAndView("redirect:/pet/detalhes/" + id);
            ModelAndView mv = new ModelAndView("/pet/edicao_status");
            mv.addObject("pet", PetDadosRequest.toDto(op.get()));
            mv.addObject("id", id);

            return mv;
        }

        return new ModelAndView("redirect:/pet/listar");
    }

    @PostMapping("/pet/atualizar/status/{id}")
    public ModelAndView atualizarStatus(@PathVariable Long id,@Valid @ModelAttribute("pet") PetDadosRequest dadosDto,BindingResult bd,Authentication authentication) {
        if (!petPertenceAoTutor(id, authentication)) return new ModelAndView("redirect:/pet/listar");
        Optional<Pet> petAtual = petService.fetchById(id);
        if (petAtual.isEmpty() || petAtual.get().getStatus() == StatusEnum.INATIVO) return new ModelAndView("redirect:/pet/detalhes/" + id);
        if (bd.hasErrors()) {
            ModelAndView mv = new ModelAndView("/pet/edicao_status");
            mv.addObject("id", id);

            return mv;
        }

        petService.updateStatus(id, PetDadosRequest.toEntity(dadosDto));

        return new ModelAndView("redirect:/pet/listar");
    }

    @GetMapping("/pet/editar/imagem/{id}")
    public ModelAndView retornarPaginaEdicaoImagem(@PathVariable Long id,Authentication authentication) {

        if (!petPertenceAoTutor(id, authentication)) {
            return new ModelAndView("redirect:/pet/listar");
        }

        Optional<Pet> op = petService.fetchById(id);

        if (op.isPresent()) {
            if (op.get().getStatus() == StatusEnum.INATIVO) {
                return new ModelAndView("redirect:/pet/detalhes/" + id);
            }

            ModelAndView mv = new ModelAndView("/pet/edicao_imagem");
            mv.addObject("pet", op.get());
            mv.addObject("id", id);

            return mv;
        }

        return new ModelAndView("redirect:/pet/listar");
    }

    @PostMapping("/pet/atualizar/imagem/{id}")
    public ModelAndView atualizarImagem(@PathVariable Long id,@RequestParam("imagem") MultipartFile imagem,Authentication authentication) {
        if (!petPertenceAoTutor(id, authentication)) {
            return new ModelAndView("redirect:/pet/listar");
        }

        Optional<Pet> petAtual = petService.fetchById(id);

        if (petAtual.isEmpty() || petAtual.get().getStatus() == StatusEnum.INATIVO) {
            return new ModelAndView("redirect:/pet/detalhes/" + id);
        }

        if (imagem == null || imagem.isEmpty()) {
            return new ModelAndView("redirect:/pet/editar/imagem/" + id + "?erro=sem-imagem");
        }

        try {
            String caminho = imagemPetService.salvar(imagem);

            Pet patch = new Pet();
            patch.setUrlImg(caminho);

            petService.updateImage(id, patch);

        } catch (IOException | IllegalArgumentException e) {
            return new ModelAndView("redirect:/pet/editar/imagem/" + id + "?erro=upload");
        }

        return new ModelAndView("redirect:/pet/detalhes/" + id);
    }

    private boolean petPertenceAoTutor(Long id,Authentication authentication) {
        var tutor = tutorService.fetchByEmail(authentication.getName());
        return tutor.isPresent() && tutor.get().getPets().stream().anyMatch(pet -> pet.getId().equals(id));
    }
}
