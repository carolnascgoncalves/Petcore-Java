package br.com.fiap.petcore.control.dtos;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.fiap.petcore.model.Exame;
import br.com.fiap.petcore.model.Prontuario;
import br.com.fiap.petcore.model.Receita;

public class ProntuarioResponse {

    private Long id;
    private LocalDateTime data;
    private String descricao;
    private Long idMedico;
    private String nomeMedico;
    private Set<Long> idExames;
    private Set<Long> idReceitas;
    private Long idHistorico;

    public static ProntuarioResponse toDto(final Prontuario prontuario) {
        ProntuarioResponse prontuarioDto = new ProntuarioResponse();

        prontuarioDto.setId(prontuario.getId());
        prontuarioDto.setData(prontuario.getData());
        prontuarioDto.setDescricao(prontuario.getDescricao());
        prontuarioDto.setIdMedico(prontuario.getMedico() != null ? prontuario.getMedico().getId() : null);
        prontuarioDto.setIdHistorico(prontuario.getHistorico() != null ? prontuario.getHistorico().getId() : null);
        prontuarioDto.setNomeMedico(prontuario.getMedico() != null ? prontuario.getMedico().getNome() : null);

        prontuarioDto.setIdExames(
                prontuario.getExames()
                        .stream()
                        .map(Exame::getId)
                        .collect(Collectors.toSet())
        );

        prontuarioDto.setIdReceitas(
                prontuario.getReceitas()
                        .stream()
                        .map(Receita::getId)
                        .collect(Collectors.toSet())
        );

        return prontuarioDto;
    }


	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public Set<Long> getIdExames() {
        return idExames;
    }

    public void setIdExames(Set<Long> idExames) {
        this.idExames = idExames;
    }

    public Set<Long> getIdReceitas() {
        return idReceitas;
    }

    public void setIdReceitas(Set<Long> idReceitas) {
        this.idReceitas = idReceitas;
    }

    public Long getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(Long idHistorico) {
        this.idHistorico = idHistorico;
    }
}