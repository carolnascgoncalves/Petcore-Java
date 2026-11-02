package br.com.fiap.petcore.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name="pet_petcore")
public class Pet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message= "O nome é obrigatorio")
    @Size(min = 3, max=100, message="O nome deve ter entre 2 à 100 caracteres")
    private String nome;

	@NotEmpty(message= "A especie é obrigatoria")
    @Size(min = 3, max=100, message="A especie deve ter entre 2 à 150 caracteres")
    private String especie;

	@NotEmpty(message= "A raca é obrigatoria")
    @Size(min = 5, max=150, message="A raca deve ter entre 2 à 150 caracteres")
    private String raca;

	@DateTimeFormat(iso = ISO.DATE)
	@PastOrPresent(message = "A data de nascimento não pode ser futura nem presente.")
	private LocalDate dataNascimento;

    @NotEmpty(message= "A pelagem é obrigatoria")
    @Size(min =5, max=150, message="A pelagem deve ter entre 2 à 100 caracteres")
    private String pelagem;

    @NotEmpty(message= "O porte é obrigatorio")
    @Size(min = 5, max=25, message="O porte deve ter entre 2 à 25 caracteres")
    private String porte;

    @NotNull(message = "O sexo é obrigatório")
    @Enumerated(EnumType.STRING)
    private SexoEnum sexo;

    private StatusEnum status;

    private String urlImg;

    @JsonIgnore
    @ManyToMany( mappedBy = "pets", fetch = FetchType.LAZY)
    private Set<Tutor> tutores;

    @OneToOne
    @JoinColumn(name = "ID_hist_(FK)", unique = true)
    private Historico historico;

    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY)
    private Set<Exame> exames;

    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY)
    private Set<Receita> receitas;


    public int calcularIdade() {
        LocalDate nascimento = dataNascimento;
        return Period.between(nascimento, LocalDate.now()).getYears();
    }

	public String getNomesTutores() {
		if (tutores == null || tutores.isEmpty()) return "Sem tutor";
		return tutores.stream().map(Tutor::getNome).sorted().collect(Collectors.joining(", "));
	}

    public Pet() {}
    
	public Pet(Long id,String nome, String especie,String raca,LocalDate dataNascimento,String pelagem,String porte,SexoEnum sexo,
			StatusEnum status, String urlImg, Set<Tutor> tutores, Historico historico) {
		this.id = id;
		this.nome = nome;
		this.especie = especie;
		this.raca = raca;
		this.dataNascimento = dataNascimento;
		this.pelagem = pelagem;
		this.porte = porte;
		this.sexo = sexo;
		this.status = status;
		this.urlImg = urlImg;
		this.tutores = tutores;
		this.historico = historico;
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


	public String getEspecie() {
		return especie;
	}


	public void setEspecie(String especie) {
		this.especie = especie;
	}


	public String getRaca() {
		return raca;
	}


	public void setRaca(String raca) {
		this.raca = raca;
	}


	public LocalDate getDataNascimento() {
		return dataNascimento;
	}


	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}


	public String getPelagem() {
		return pelagem;
	}


	public void setPelagem(String pelagem) {
		this.pelagem = pelagem;
	}


	public String getPorte() {
		return porte;
	}


	public void setPorte(String porte) {
		this.porte = porte;
	}


	public SexoEnum getSexo() {
		return sexo;
	}


	public void setSexo(SexoEnum sexo) {
		this.sexo = sexo;
	}


	public StatusEnum getStatus() {
		return status;
	}


	public void setStatus(StatusEnum status) {
		this.status = status;
	}


	public String getUrlImg() {
		return urlImg;
	}


	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}


	public Set<Tutor> getTutores() {
		return tutores;
	}


	public void setTutores(Set<Tutor> tutores) {
		this.tutores = tutores;
	}


	public Historico getHistorico() {
		return historico;
	}


	public void setHistorico(Historico historico) {
		this.historico = historico;
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

}