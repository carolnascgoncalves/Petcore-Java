package br.com.fiap.petcore.control.dtos;

import br.com.fiap.petcore.model.Medicamento;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class MedicamentoDadosRequest {

    @NotEmpty(message = "A dosagem é obrigatória")
    @Size(min = 2, max = 15, message = "A dosagem deve ter entre 2 e 15 caracteres")
    private String dosagem;

    public static Medicamento toEntity(final MedicamentoDadosRequest dto) {
        Medicamento medicamento = new Medicamento();
        medicamento.setDosagem(dto.getDosagem());
        return medicamento;
    }

    public static MedicamentoDadosRequest toDto(final Medicamento medicamento) {
        MedicamentoDadosRequest medicamentoDto = new MedicamentoDadosRequest();
        medicamentoDto.setDosagem(medicamento.getDosagem());
        return medicamentoDto;
    }

    public String getDosagem() { return dosagem; }
    public void setDosagem(String dosagem) { this.dosagem = dosagem; }
}
