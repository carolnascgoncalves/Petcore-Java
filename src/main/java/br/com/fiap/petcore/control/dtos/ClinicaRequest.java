package br.com.fiap.petcore.control.dtos;

import br.com.fiap.petcore.model.Clinica;
import br.com.fiap.petcore.model.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ClinicaRequest {

    @NotBlank(message = "O CNPJ é obrigatório")
    @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter exatamente 14 dígitos")
    private String cnpj;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "\\d{8}", message = "O CEP deve conter exatamente 8 dígitos")
    private String cep;

    @Pattern(regexp = "^$|.{5,200}$", message = "O complemento deve ter entre 5 e 200 caracteres")
    private String complemento;

    public static Clinica toEntity(final ClinicaRequest dto) {
        Clinica clinica = new Clinica();
        Endereco endereco = new Endereco();
        endereco.setCep(dto.getCep());
        endereco.setComplemento(dto.getComplemento() == null || dto.getComplemento().isBlank() ? null : dto.getComplemento());
        clinica.setCnpj(dto.getCnpj());
        clinica.setNome(dto.getNome());
        clinica.setEndereco(endereco);
        return clinica;
    }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }
}
