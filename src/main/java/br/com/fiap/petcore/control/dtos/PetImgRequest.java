package br.com.fiap.petcore.control.dtos;

import org.hibernate.validator.constraints.URL;

import br.com.fiap.petcore.model.Pet;

public class PetImgRequest {

    @URL(message = "A URL da imagem deve ser válida")
    private String urlImg;

    public static Pet toEntity(final PetImgRequest dto) {
        Pet pet = new Pet();

        pet.setUrlImg(dto.getUrlImg());

        return pet;
    }

    public static PetImgRequest toDto(final Pet pet) {
        PetImgRequest petDto = new PetImgRequest();

        petDto.setUrlImg(pet.getUrlImg());

        return petDto;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}