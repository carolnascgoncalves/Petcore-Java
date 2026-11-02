package br.com.fiap.petcore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name="clinica_petcore")
public class Clinica {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @NotBlank(message = "O cnpj é obrigatorio")
    @Pattern(regexp = "\\d{14}", message = "O cnpj deve conter exatamente 14 dígitos")
    private String cnpj;

    @NotBlank(message = "O nome é obrigatorio")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 à 100 caracteres")
    private String nome;
    
    @ManyToMany( fetch = FetchType.LAZY)
    @JoinTable(name = "cli_rel_petcore",joinColumns = @JoinColumn(name="ID_cli_(FK)"),inverseJoinColumns = @JoinColumn(name="ID_rel_(FK)"))
    private Set<Relatorio> relatorios;

    @OneToOne
    @JoinColumn(name = "ID_end_(FK)", unique = true)
    private Endereco endereco;

    public Clinica(long id, String cnpj, String nome, Set<Relatorio> relatorios, Endereco endereco) {
    	this.id = id;
    	this.cnpj = cnpj;
    	this.nome = nome;
    	this.relatorios = relatorios;
    	this.endereco = endereco;
    }
    
    public Clinica() {}
    
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

	public Set<Relatorio> getRelatorios() {
		return relatorios;
	}

	public void setRelatorios(Set<Relatorio> relatorios) {
		this.relatorios = relatorios;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}