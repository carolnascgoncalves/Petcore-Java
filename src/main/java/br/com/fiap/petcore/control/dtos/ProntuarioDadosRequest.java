package br.com.fiap.petcore.control.dtos;

import br.com.fiap.petcore.model.Prontuario;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ProntuarioDadosRequest {

    @NotEmpty(message = "A descrição é obrigatória")
    @Size(min = 5, max = 500, message = "A descrição deve ter entre 5 e 500 caracteres")
    private String descricao;

    public static Prontuario toEntity(final ProntuarioDadosRequest dto) {
        Prontuario prontuario = new Prontuario();
        prontuario.setDescricao(dto.getDescricao());
        return prontuario;
    }

    public static ProntuarioDadosRequest toDto(final Prontuario prontuario) {
        ProntuarioDadosRequest prontuarioDto = new ProntuarioDadosRequest();
        prontuarioDto.setDescricao(prontuario.getDescricao());
        return prontuarioDto;
    }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
