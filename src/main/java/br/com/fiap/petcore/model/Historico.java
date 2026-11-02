package br.com.fiap.petcore.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="historico_petcore")
public class Historico {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime data;

    private StatusEnum status;
    
    @OneToOne(mappedBy = "historico")
    private Pet pet;

    @OneToMany(mappedBy = "historico", fetch = FetchType.LAZY)
    private Set<Relatorio> relatorios;

    @OneToMany(mappedBy = "historico", fetch = FetchType.LAZY)
    private Set<Prontuario> prontuarios;

    public Historico() {}
	public Historico(Long id, LocalDateTime data, StatusEnum status, Pet pet, Set<Relatorio> relatorios, Set<Prontuario> prontuarios) {
		this.id = id;
		this.data = data;
		this.status = status;
		this.pet = pet;
		this.relatorios = relatorios;
		this.prontuarios = prontuarios;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public Set<Relatorio> getRelatorios() {
		return relatorios;
	}

	public void setRelatorios(Set<Relatorio> relatorios) {
		this.relatorios = relatorios;
	}

	public Set<Prontuario> getProntuarios() {
		return prontuarios;
	}

	public void setProntuarios(Set<Prontuario> prontuarios) {
		this.prontuarios = prontuarios;
	}

    
    
    

    public String getDataFormatada() {
        if (data == null) return "";
        return data.format(DateTimeFormatter.ofPattern("HH:mm'h' dd/MM/yyyy"));
    }
}