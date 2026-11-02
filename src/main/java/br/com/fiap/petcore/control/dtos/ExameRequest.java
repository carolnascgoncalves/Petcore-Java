package br.com.fiap.petcore.control.dtos;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.fiap.petcore.model.Exame;
import br.com.fiap.petcore.model.Pet;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ExameRequest {

    @NotEmpty(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotEmpty(message = "O tipo do exame é obrigatório")
    @Size(min = 5, max = 100, message = "O tipo do exame deve ter entre 5 e 100 caracteres")
    private String tipo;
    
    @NotNull(message = "A data e hora são obrigatórias")
    @FutureOrPresent(message = "A data e hora do exame não podem estar no passado")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime data;

    @NotNull(message = "O pet é obrigatório")
    private Long idPet;

    public static Exame toEntity(final ExameRequest dto) {
        Exame exame = new Exame();
        exame.setNome(dto.getNome());
        exame.setData(dto.getData());
        exame.setTipo(dto.getTipo());
        Pet pet = new Pet();
        pet.setId(dto.getIdPet());
        exame.setPet(pet);
        return exame;
    }

    public static ExameRequest toDto(final Exame exame) {
        ExameRequest exameDto = new ExameRequest();
        exameDto.setNome(exame.getNome());
        exameDto.setTipo(exame.getTipo());
        exameDto.setIdPet(exame.getPet().getId());
        return exameDto;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Long getIdPet() { return idPet; }
    public void setIdPet(Long idPet) { this.idPet = idPet; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
}
