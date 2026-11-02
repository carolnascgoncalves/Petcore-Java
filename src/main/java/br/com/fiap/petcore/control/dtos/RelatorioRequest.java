package br.com.fiap.petcore.control.dtos;

import br.com.fiap.petcore.model.Historico;
import br.com.fiap.petcore.model.Relatorio;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RelatorioRequest {

    @Size(min = 5, max = 500, message = "A observação deve ter entre 5 e 500 caracteres")
    private String observacao;

    @NotNull(message = "O histórico é obrigatório")
    private Long idHist;

    public static Relatorio toEntity(final RelatorioRequest dto) {
        Relatorio relatorio = new Relatorio();
        Historico historico = new Historico();
        historico.setId(dto.getIdHist());
        relatorio.setObservacao(dto.getObservacao());
        relatorio.setHistorico(historico);
        return relatorio;
    }

    public static RelatorioRequest toDto(final Relatorio relatorio) {
        RelatorioRequest relatorioDto = new RelatorioRequest();
        relatorioDto.setIdHist(relatorio.getHistorico().getId());
        relatorioDto.setObservacao(relatorio.getObservacao());
        return relatorioDto;
    }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public Long getIdHist() { return idHist; }
    public void setIdHist(Long idHist) { this.idHist = idHist; }
}
