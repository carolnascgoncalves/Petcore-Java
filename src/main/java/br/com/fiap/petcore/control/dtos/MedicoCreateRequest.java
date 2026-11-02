package br.com.fiap.petcore.control.dtos;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import br.com.fiap.petcore.model.Medico;
import br.com.fiap.petcore.model.SexoEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class MedicoCreateRequest {

    @NotEmpty(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotNull(message = "A data de nascimento é obrigatória")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dataNascimento;

    @NotEmpty(message = "O telefone é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "O telefone deve conter exatamente 11 dígitos")
    private String telefone;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    @Size(min = 5, max = 100, message = "O email deve ter entre 5 e 100 caracteres.")
    private String email;

    @NotNull(message = "O sexo é obrigatório")
    private SexoEnum sexo;

    @NotEmpty(message = "A senha é obrigatória")
    @Size(min = 5, max = 100, message = "A senha deve ter entre 5 e 100 caracteres")
    private String senha;

    @NotEmpty(message = "A especialidade é obrigatória")
    @Size(min = 5, max = 200, message = "A especialidade deve ter entre 5 e 200 caracteres")
    private String especialidade;


    public static Medico toEntity(final MedicoCreateRequest dto) {
        Medico medico = new Medico();

        medico.setNome(dto.getNome());
        medico.setDataNascimento(dto.getDataNascimento());
        medico.setTelefone(dto.getTelefone());
        medico.setEmail(dto.getEmail());
        medico.setSexo(dto.getSexo());
        medico.setSenha(dto.getSenha());
        medico.setEspecialidade(dto.getEspecialidade());

        return medico;
    }

    public static MedicoCreateRequest toDto(final Medico medico) {
        MedicoCreateRequest medicoDto = new MedicoCreateRequest();

        medicoDto.setNome(medico.getNome());
        medicoDto.setDataNascimento(medico.getDataNascimento());
        medicoDto.setTelefone(medico.getTelefone());
        medicoDto.setEmail(medico.getEmail());
        medicoDto.setSexo(medico.getSexo());
        medicoDto.setSenha(medico.getSenha());
        medicoDto.setEspecialidade(medico.getEspecialidade());

        return medicoDto;
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
}