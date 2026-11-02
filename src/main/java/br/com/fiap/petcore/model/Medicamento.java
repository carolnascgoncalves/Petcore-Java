package br.com.fiap.petcore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name="medicamento_petcore")
public class Medicamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "O nome é obrigatorio")
    @Size(min = 3,max = 100, message = "O nome deve ter entre 2 à 100 caracteres")
    private String nome;

	@NotEmpty(message = "A dosagem é obrigatoria")
    @Size(min = 2, max = 15, message = "A dosagem deve ter entre 2 e 15 caracteres")
    private String dosagem;


    @JsonIgnore
    @ManyToMany(mappedBy = "medicamentos")
    private Set<Receita> receitas;

    public Medicamento() {}
    
	public Medicamento(Long id,String nome, String dosagem,Set<Receita> receitas) {
		this.id = id;
		this.nome = nome;
		this.dosagem = dosagem;
		this.receitas = receitas;
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


	public String getDosagem() {
		return dosagem;
	}


	public void setDosagem(String dosagem) {
		this.dosagem = dosagem;
	}


	public Set<Receita> getReceitas() {
		return receitas;
	}


	public void setReceitas(Set<Receita> receitas) {
		this.receitas = receitas;
	}

    

}