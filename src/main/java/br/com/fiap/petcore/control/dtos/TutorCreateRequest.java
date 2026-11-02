package br.com.fiap.petcore.control.dtos;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.fiap.petcore.model.SexoEnum;
import br.com.fiap.petcore.model.Tutor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class TutorCreateRequest {

	@NotBlank(message = "O nome é obrigatório.")
	@Size(min = 3, max = 100, message = "O Nome deve ter entre 3 e 100 caracteres.")
	private String nome;

	@NotNull(message = "A data de nascimento é obrigatória")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataNascimento;

	@NotBlank(message = "O telefone é obrigatório.")
	@Pattern(regexp = "\\d{11}", message = "O telefone deve conter exatamente 11 dígitos")
	private String telefone;
	
    @NotNull(message = "O sexo é obrigatório")
    private SexoEnum sexo;
    
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    @Size(min = 5, max = 100, message = "O email deve ter entre 5 e 100 caracteres.")
    private String email;
    
    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 5, max = 30, message = "A senha deve ter entre 5 e 30 caracteres.")
    private String senha;

    public static Tutor toEntity(final TutorCreateRequest dto) {
        Tutor tutor = new Tutor();

        tutor.setNome(dto.getNome());
        tutor.setDataNascimento(dto.getDataNascimento());
        tutor.setTelefone(dto.getTelefone());
        tutor.setSexo(dto.getSexo());
        tutor.setEmail(dto.getEmail());
        tutor.setSenha(dto.getSenha());

        return tutor;
    }

    public static TutorCreateRequest toDto(final Tutor tutor) {
        TutorCreateRequest tutorDto = new TutorCreateRequest();

        tutorDto.setNome(tutor.getNome());
        tutorDto.setDataNascimento(tutor.getDataNascimento());
        tutorDto.setTelefone(tutor.getTelefone());
        tutorDto.setSexo(tutor.getSexo());
        tutorDto.setEmail(tutor.getEmail());
        tutorDto.setSenha(tutor.getSenha());

        return tutorDto;
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

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}