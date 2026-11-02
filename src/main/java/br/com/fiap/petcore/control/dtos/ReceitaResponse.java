package br.com.fiap.petcore.control.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.fiap.petcore.model.Medicamento;
import br.com.fiap.petcore.model.Receita;

public class ReceitaResponse {

    private Long id;
    private String nome;
    private String descricao;
    private LocalDateTime data;
    private LocalDate validade;
    private Long idMedico;
    private String nomeMedico;
    private String urlImgMed;
    private Set<Long> idMedicamentos;

    public static ReceitaResponse toDto(final Receita receita) {
        ReceitaResponse receitaDto = new ReceitaResponse();

        receitaDto.setId(receita.getId());
        receitaDto.setNome(receita.getNome());
        receitaDto.setDescricao(receita.getDescricao());
        receitaDto.setData(receita.getData());
        receitaDto.setValidade(receita.getValidade());
        receitaDto.setIdMedico(receita.getMedico() != null ? receita.getMedico().getId() : null);
        receitaDto.setNomeMedico(receita.getMedico() != null ? receita.getMedico().getNome() : null);
        receitaDto.setUrlImgMed(receita.getMedico() != null ? receita.getMedico().getUrlImg() : null);
        receitaDto.setIdMedicamentos(
                receita.getMedicamentos()
                        .stream()
                        .map(Medicamento::getId)
                        .collect(Collectors.toSet())
        );

        return receitaDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
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

    public String getUrlImgMed() {
        return urlImgMed;
    }

    public void setUrlImgMed(String urlImgMed) {
        this.urlImgMed = urlImgMed;
    }

    public Set<Long> getIdMedicamentos() {
        return idMedicamentos;
    }

    public void setIdMedicamentos(Set<Long> idMedicamentos) {
        this.idMedicamentos = idMedicamentos;
    }
}