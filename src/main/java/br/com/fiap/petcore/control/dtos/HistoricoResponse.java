package br.com.fiap.petcore.control.dtos;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.fiap.petcore.model.Historico;
import br.com.fiap.petcore.model.Prontuario;
import br.com.fiap.petcore.model.StatusEnum;

public class HistoricoResponse {

    private Long id;
    private StatusEnum status = StatusEnum.ATIVO;
    private Set<Long> idProntuarios;
    private Long idPet;

    public static HistoricoResponse toDto(final Historico historico) {
        HistoricoResponse historicoDto = new HistoricoResponse();

        historicoDto.setId(historico.getId());
        historicoDto.setStatus(historico.getStatus());
        historicoDto.setIdProntuarios(
                historico.getProntuarios()
                        .stream()
                        .map(Prontuario::getId)
                        .collect(Collectors.toSet())
        );
        historicoDto.setIdPet(historico.getPet() != null ? historico.getPet().getId() : null);

        return historicoDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Set<Long> getIdProntuarios() {
        return idProntuarios;
    }

    public void setIdProntuarios(Set<Long> idProntuarios) {
        this.idProntuarios = idProntuarios;
    }

    public Long getIdPet() {
        return idPet;
    }

    public void setIdPet(Long idPet) {
        this.idPet = idPet;
    }
}