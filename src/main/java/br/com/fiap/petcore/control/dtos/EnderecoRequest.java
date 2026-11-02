package br.com.fiap.petcore.control.dtos;

import br.com.fiap.petcore.model.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EnderecoRequest {

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "\\d{8}", message = "O CEP deve conter exatamente 8 dígitos")
    private String cep;

    @Size(min = 5, max = 200, message = "O complemento deve ter entre 5 e 200 caracteres")
    private String complemento;

    public static Endereco toEntity(final EnderecoRequest dto) {
        Endereco endereco = new Endereco();
        endereco.setCep(dto.getCep());
        endereco.setComplemento(dto.getComplemento());
        return endereco;
    }

    public static EnderecoRequest toDto(final Endereco endereco) {
        EnderecoRequest enderecoDto = new EnderecoRequest();
        enderecoDto.setCep(endereco.getCep());
        enderecoDto.setComplemento(endereco.getComplemento());
        return enderecoDto;
    }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }
}
