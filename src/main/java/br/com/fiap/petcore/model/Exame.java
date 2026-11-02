package br.com.fiap.petcore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="exame_petcore")
public class Exame {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message= "O nome é obrigatorio")
    @Size(min=3,max=100, message="O nome deve ter entre 2 à 100 caracteres")
    @Column(name="NOME_ex", length = 100, nullable = false)
    private String nome;


	@DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime data;

	@NotEmpty(message= "O tipo do exame é obrigatorio")
    @Size(min=5,max=100, message="O tipo do exame deve ter entre 2 à 100 caracteres")
    @Column(name="TP_ex", length = 100, nullable = false)
    private String tipo;


    @ManyToOne
    @JoinColumn(name = "ID_med_(PK)")
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "ID_pront_(PK)")
    private Prontuario prontuario;

    @ManyToOne
    @JoinColumn(name = "ID_pet_(FK)")
    private Pet pet;

    private boolean removido = false;

    public Exame() {}
    
    public Exame(Long id, String nome, LocalDateTime data, String tipo, Medico medico, Prontuario prontuario) {
    	this.id = id;
    	this.nome = nome;
    	this.data = data;
    	this.tipo = tipo;
    	this.medico = medico;
    	this.prontuario = prontuario;
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

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public boolean isRemovido() {
		return removido;
	}

	public void setRemovido(boolean removido) {
		this.removido = removido;
	}

	public Pet getPet() { return pet; }
	public void setPet(Pet pet) { this.pet = pet; }

	public Prontuario getProntuario() {
		return prontuario;
	}

	public void setProntuario(Prontuario prontuario) {
		this.prontuario = prontuario;
	}


    

    public String getDataFormatada() {
        if (data == null) return "";
        return data.format(DateTimeFormatter.ofPattern("HH:mm'h' dd/MM/yyyy"));
    }
}