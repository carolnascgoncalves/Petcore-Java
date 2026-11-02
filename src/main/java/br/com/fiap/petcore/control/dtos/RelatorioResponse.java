package br.com.fiap.petcore.control.dtos;

import br.com.fiap.petcore.model.Relatorio;

public class RelatorioResponse {

    private Long id;
    private String observacao;
    private Long idHist;
    private Long idMedico;

    public static RelatorioResponse toDto(final Relatorio relatorio) {
        RelatorioResponse relatorioDto = new RelatorioResponse();

        relatorioDto.setId(relatorio.getId());
        relatorioDto.setObservacao(relatorio.getObservacao());
        relatorioDto.setIdHist(relatorio.getHistorico() != null ? relatorio.getHistorico().getId() : null);
        relatorioDto.setIdMedico(relatorio.getMedico() != null ? relatorio.getMedico().getId() : null);

        return relatorioDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Long getIdHist() {
        return idHist;
    }

    public void setIdHist(Long idHist) {
        this.idHist = idHist;
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }
}