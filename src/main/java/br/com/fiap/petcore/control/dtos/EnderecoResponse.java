package br.com.fiap.petcore.control.dtos;

import br.com.fiap.petcore.model.Endereco;

public class EnderecoResponse {

    private Long id;
    private String cep;
    private String complemento;
    private Long idClinica;

    public static EnderecoResponse toDto(final Endereco endereco) {
        EnderecoResponse enderecoDto = new EnderecoResponse();

        enderecoDto.setId(endereco.getId());
        enderecoDto.setCep(endereco.getCep());
        enderecoDto.setComplemento(endereco.getComplemento());
        enderecoDto.setIdClinica(endereco.getClinica() != null ? endereco.getClinica().getId() : null);

        return enderecoDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Long getIdClinica() {
        return idClinica;
    }

    public void setIdClinica(Long idClinica) {
        this.idClinica = idClinica;
    }
}