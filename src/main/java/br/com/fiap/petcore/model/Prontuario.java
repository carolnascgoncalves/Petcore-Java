package br.com.fiap.petcore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="prontuario_petcore")
public class Prontuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "A data é obrigatória")
    private LocalDateTime data;

	@NotEmpty(message= "A descricao é obrigatoria")
    @Size(min=5,max=500, message="A descricao deve ter no máximo 500 caracteres")
    private String descricao;


    @ManyToOne
    @JoinColumn(name = "ID_med_(PK)")
    private Medico medico;

    @OneToMany(mappedBy = "prontuario", fetch = FetchType.LAZY)
    private Set<Exame> exames = new HashSet<>();

    @OneToMany(mappedBy = "prontuario", fetch = FetchType.LAZY)
    private Set<Receita> receitas = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "ID_hist_(PK)")
    private Historico historico;

    public Prontuario() {}
    
	public Prontuario(Long id,LocalDateTime data,String descricao,Medico medico, Set<Exame> exames, Set<Receita> receitas, Historico historico) {
		this.id = id;
		this.data = data;
		this.descricao = descricao;
		this.medico = medico;
		this.exames = exames;
		this.receitas = receitas;
		this.historico = historico;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Set<Exame> getExames() {
		return exames;
	}

	public void setExames(Set<Exame> exames) {
		this.exames = exames;
	}

	public Set<Receita> getReceitas() {
		return receitas;
	}

	public void setReceitas(Set<Receita> receitas) {
		this.receitas = receitas;
	}

	public Historico getHistorico() {
		return historico;
	}

	public void setHistorico(Historico historico) {
		this.historico = historico;
	}
	
	
    
    	

    public String getDataFormatada() {
        if (data == null) return "";
        return data.format(DateTimeFormatter.ofPattern("HH:mm'h' dd/MM/yyyy"));
    }
}
