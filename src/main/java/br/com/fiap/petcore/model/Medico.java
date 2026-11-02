package br.com.fiap.petcore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="medico_petcore")
public class Medico {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message= "O nome é obrigatorio")
    @Size(min = 3, max=100, message="O nome deve ter entre 2 à 100 caracteres")
    private String nome;

	@DateTimeFormat(iso = ISO.DATE)
	@Past(message = "A data de nascimento não pode ser futura nem presente.")
	private LocalDate dataNascimento;

	@NotEmpty(message= "O telefone é obrigatorio")
	@Pattern(regexp = "\\d{11}", message = "O telefone deve conter exatamente 11 dígitos")
    private String telefone;

	@NotEmpty( message = "O email é obrigatorio")
    @Email(message="O email deve ser válido")
    @Size(min = 5, max = 100, message="O email deve ter entre 5 à 100 caracteres")
    private String email;

	@NotNull(message = "O sexo é obrigatório")
    @Enumerated(EnumType.STRING)
    private SexoEnum sexo;

	@NotEmpty(message="A senha é obrigatoria")
    @Size( min=5, max=100, message="O senha deve ter entre 5 à 100 caracteres.")
    private String senha;

	@NotEmpty(message= "A especialidade é obrigatoria")
    @Size(max=200, message="A especialidade deve ter no máximo 200 caracteres")
    private String especialidade;

    private String urlImg;

    @OneToMany(mappedBy = "medico", fetch = FetchType.LAZY)
    private Set<Relatorio> relatorios;

    @OneToMany(mappedBy = "medico", fetch = FetchType.LAZY)
    private  Set<Prontuario> prontuarios;

    @OneToMany(mappedBy = "medico", fetch = FetchType.LAZY)
    private Set<Exame> exames;

    @OneToMany(mappedBy = "medico", fetch = FetchType.LAZY)
    private  Set<Receita> receitas;

	public Medico(Long id,String nome,LocalDate dataNascimento,String telefone,String email, SexoEnum sexo,String senha,String especialidade,
			String urlImg, Set<Relatorio> relatorios, Set<Prontuario> prontuarios, Set<Exame> exames,Set<Receita> receitas) {
		this.id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.telefone = telefone;
		this.email = email;
		this.sexo = sexo;
		this.senha = senha;
		this.especialidade = especialidade;
		this.urlImg = urlImg;
		this.relatorios = relatorios;
		this.prontuarios = prontuarios;
		this.exames = exames;
		this.receitas = receitas;
	}
	
	public Medico() {}

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

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public SexoEnum getSexo() {
		return sexo;
	}

	public void setSexo(SexoEnum sexo) {
		this.sexo = sexo;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public String getUrlImg() {
		return urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
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