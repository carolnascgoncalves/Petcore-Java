package br.com.fiap.petcore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="endereco_petcore")
public class Endereco {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


    @NotBlank(message = "O cep é obrigatorio")
    @Pattern(regexp = "\\d{8}", message = "O cep deve conter exatamente 8 dígitos")
    private String cep;

    @Size(min = 5,max = 200, message = "O complemento deve ter entre 2 à 200 caracteres")
    private String complemento;

    @OneToOne( mappedBy = "endereco")
    private Clinica clinica;

    public Endereco(Long id, String cep, String complemento, Clinica clinica) {
    	this.id = id;
    	this.cep = cep;
    	this.complemento = complemento;
    	this.clinica = clinica;
    }
    
    public Endereco() {}
    
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

	public Clinica getClinica() {
		return clinica;
	}
	
	public void setClinica(Clinica clinica) {
		this.clinica = clinica;
	}

    
}