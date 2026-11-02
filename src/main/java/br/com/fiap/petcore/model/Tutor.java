package br.com.fiap.petcore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="tutor_petcore")
public class Tutor {
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
    @Size( min=5, max = 100, message="O senha deve ter entre 5 à 100 caracteres.")
    private String senha;

    private String urlImg;

    @ManyToMany
    @JoinTable(name="tut_pet_petcore",
            joinColumns = @JoinColumn(name="ID_tut_(FK)"),
            inverseJoinColumns = @JoinColumn(name="ID_pet_(FK)"))
    private Set<Pet> pets = new HashSet<>();

    public Tutor() {}
    
	public Tutor(Long id,String nome,LocalDate dataNascimento,String telefone,String email,SexoEnum sexo, String senha,String urlImg, Set<Pet> pets) {
		this.id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.telefone = telefone;
		this.email = email;
		this.sexo = sexo;
		this.senha = senha;
		this.urlImg = urlImg;
		this.pets = pets;
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

	public String getUrlImg() {
		return urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}

	public Set<Pet> getPets() {
		return pets;
	}

	public void setPets(Set<Pet> pets) {
		this.pets = pets;
	}

	
    
}