package br.com.fiap.petcore.control.dtos;

import br.com.fiap.petcore.model.Pet;
import br.com.fiap.petcore.model.SexoEnum;

public class PetMenuResponse {

    private Long id;
    private String nome;
    private int idade;
    private SexoEnum sexo;
    private String urlImg;

    public static PetMenuResponse toDto(final Pet pet) {
        PetMenuResponse petDto = new PetMenuResponse();

        petDto.setId(pet.getId());
        petDto.setNome(pet.getNome());
        petDto.setIdade(pet.calcularIdade());
        petDto.setSexo(pet.getSexo());
        petDto.setUrlImg(pet.getUrlImg());

        return petDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
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
}