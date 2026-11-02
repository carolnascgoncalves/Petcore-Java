package br.com.fiap.petcore.control.dtos;

import br.com.fiap.petcore.model.Clinica;

public class ClinicaResponse {

    private Long id;
    private String cnpj;
    private String nome;
    private Long idEndereco;

    public static ClinicaResponse toDto(final Clinica clinica) {
        ClinicaResponse clinicaDto = new ClinicaResponse();

        clinicaDto.setId(clinica.getId());
        clinicaDto.setCnpj(clinica.getCnpj());
        clinicaDto.setNome(clinica.getNome());
        clinicaDto.setIdEndereco(clinica.getEndereco() != null ? clinica.getEndereco().getId() : null);

        return clinicaDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Long idEndereco) {
        this.idEndereco = idEndereco;
    }
}