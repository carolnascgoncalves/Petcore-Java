package br.com.fiap.petcore.control.dtos;

import br.com.fiap.petcore.model.Relatorio;
import jakarta.validation.constraints.Size;

public class RelatorioDadosRequest {

    @Size(min = 5, max = 500, message = "A observação deve ter entre 5 e 500 caracteres")
    private String observacao;

    public static Relatorio toEntity(final RelatorioDadosRequest dto) {
        Relatorio relatorio = new Relatorio();
        relatorio.setObservacao(dto.getObservacao());
        return relatorio;
    }

    public static RelatorioDadosRequest toDto(final Relatorio relatorio) {
        RelatorioDadosRequest relatorioDto = new RelatorioDadosRequest();
        relatorioDto.setObservacao(relatorio.getObservacao());
        return relatorioDto;
    }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
