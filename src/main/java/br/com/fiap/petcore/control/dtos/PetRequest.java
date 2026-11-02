package br.com.fiap.petcore.control.dtos;

import java.time.LocalDate;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.fiap.petcore.model.Pet;
import br.com.fiap.petcore.model.SexoEnum;
import br.com.fiap.petcore.model.StatusEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public class PetRequest {

    @NotEmpty(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotNull(message = "A data de nascimento é obrigatória")
    @PastOrPresent(message = "A data de nascimento não pode ser futura")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dataNasc;

    @NotNull(message = "O sexo é obrigatório")
    private SexoEnum sexo;

    @URL(message = "A URL da imagem deve ser válida")
    private String urlImg;

    @NotEmpty(message = "O porte é obrigatório")
    @Size(min = 5, max = 25, message = "O porte deve ter entre 5 e 25 caracteres")
    private String porte;

    @NotEmpty(message = "A espécie é obrigatória")
    @Size(min = 5, max = 100, message = "A espécie deve ter entre 5 e 100 caracteres")
    private String especie;

    @NotEmpty(message = "A raça é obrigatória")
    @Size(min = 5, max = 150, message = "A raça deve ter entre 5 e 150 caracteres")
    private String raca;

    @NotEmpty(message = "A pelagem é obrigatória")
    @Size(min = 5, max = 150, message = "A pelagem deve ter entre 5 e 150 caracteres")
    private String pelagem;

    public static Pet toEntity(final PetRequest dto) {
        Pet pet = new Pet();

        pet.setNome(dto.getNome());
        pet.setDataNascimento(dto.getDataNasc());
        pet.setSexo(dto.getSexo());
        pet.setUrlImg(dto.getUrlImg());
        pet.setStatus(StatusEnum.ATIVO);
        pet.setPorte(dto.getPorte());
        pet.setEspecie(dto.getEspecie());
        pet.setRaca(dto.getRaca());
        pet.setPelagem(dto.getPelagem());

        return pet;
    }

    public static PetRequest toDto(final Pet pet) {
        PetRequest petDto = new PetRequest();

        petDto.setNome(pet.getNome());
        petDto.setDataNasc(pet.getDataNascimento());
        petDto.setSexo(pet.getSexo());
        petDto.setUrlImg(pet.getUrlImg());
        petDto.setPorte(pet.getPorte());
        petDto.setEspecie(pet.getEspecie());
        petDto.setRaca(pet.getRaca());
        petDto.setPelagem(pet.getPelagem());

        return petDto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getPelagem() {
        return pelagem;
    }

    public void setPelagem(String pelagem) {
        this.pelagem = pelagem;
    }
}