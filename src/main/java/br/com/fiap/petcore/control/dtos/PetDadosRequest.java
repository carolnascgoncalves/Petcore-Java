package br.com.fiap.petcore.control.dtos;

import br.com.fiap.petcore.model.Pet;
import br.com.fiap.petcore.model.StatusEnum;
import jakarta.validation.constraints.NotNull;

public class PetDadosRequest {

    @NotNull(message = "O status é obrigatório")
    private StatusEnum statusEnum;

    public static Pet toEntity(final PetDadosRequest dto) {
        Pet pet = new Pet();

        pet.setStatus(dto.getStatusEnum());

        return pet;
    }

    public static PetDadosRequest toDto(final Pet pet) {
        PetDadosRequest petDto = new PetDadosRequest();

        petDto.setStatusEnum(pet.getStatus());

        return petDto;
    }

    public StatusEnum getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(StatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }
}