package br.com.fiap.petcore.control.dtos;

import java.time.LocalDateTime;

import br.com.fiap.petcore.model.Exame;

public class ExameResponse {

    private Long id;
    private String nome;
    private LocalDateTime data;
    private String tipo;
    private Long idMedico;
    private String nomeMedico;
    private String urlImgMed;
    private Long idProntuario;

    public static ExameResponse toDto(final Exame exame) {
        ExameResponse exameDto = new ExameResponse();

        exameDto.setId(exame.getId());
        exameDto.setNome(exame.getNome());
        exameDto.setData(exame.getData());
        exameDto.setTipo(exame.getTipo());
        exameDto.setIdMedico(exame.getMedico() != null ? exame.getMedico().getId() : null);
        exameDto.setNomeMedico(exame.getMedico() != null ? exame.getMedico().getNome() : null);
        exameDto.setUrlImgMed(exame.getMedico() != null ? exame.getMedico().getUrlImg() : null);
        exameDto.setIdProntuario(exame.getProntuario() != null ? exame.getProntuario().getId() : null);

        return exameDto;
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

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public Long getIdProntuario() {
        return idProntuario;
    }

    public void setIdProntuario(Long idProntuario) {
        this.idProntuario = idProntuario;
    }
}