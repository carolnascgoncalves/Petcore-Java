package br.com.fiap.petcore.control.dtos;

import br.com.fiap.petcore.model.Medicamento;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class MedicamentoRequest {

    @NotEmpty(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotEmpty(message = "A dosagem é obrigatória")
    @Size(min = 2, max = 15, message = "A dosagem deve ter entre 2 e 15 caracteres")
    private String dosagem;


    public static Medicamento toEntity(final MedicamentoRequest dto) {
        Medicamento medicamento = new Medicamento();
        medicamento.setNome(dto.getNome());
        medicamento.setDosagem(dto.getDosagem());
        return medicamento;
    }

    public static MedicamentoRequest toDto(final Medicamento medicamento) {
        MedicamentoRequest medicamentoDto = new MedicamentoRequest();
        medicamentoDto.setNome(medicamento.getNome());
        medicamentoDto.setDosagem(medicamento.getDosagem());
        return medicamentoDto;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDosagem() { return dosagem; }
    public void setDosagem(String dosagem) { this.dosagem = dosagem; }
}
