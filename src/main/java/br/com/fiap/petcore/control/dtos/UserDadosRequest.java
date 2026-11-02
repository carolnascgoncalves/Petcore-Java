package br.com.fiap.petcore.control.dtos;

import org.hibernate.validator.constraints.URL;

import br.com.fiap.petcore.model.Medico;
import br.com.fiap.petcore.model.Tutor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDadosRequest {

    @NotEmpty(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    @Size(min = 5, max = 100, message = "O email deve ter entre 5 e 100 caracteres")
    private String email;

    @NotEmpty(message = "O telefone é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "O telefone deve conter exatamente 11 dígitos")
    private String telefone;

    @Size(min = 5, max = 100, message = "A senha deve ter entre 5 e 100 caracteres")
    private String senha;

    @URL(message = "A URL da imagem deve ser válida")
    private String urlImg;

    public static UserDadosRequest toDto(final Tutor tutor){
        UserDadosRequest tutorDto = new UserDadosRequest();
        tutorDto.setEmail(tutor.getEmail());
        tutorDto.setTelefone(tutor.getTelefone());
        tutorDto.setSenha(tutor.getSenha());
        tutorDto.setUrlImg(tutor.getUrlImg());

        return tutorDto;
    }

    public static Tutor toEntity(final UserDadosRequest dto){
        Tutor tutor = new Tutor();
        tutor.setEmail(dto.getEmail());
        tutor.setTelefone(dto.getTelefone());
        tutor.setSenha(dto.getSenha());
        tutor.setUrlImg(dto.getUrlImg());

        return tutor;
    }

    public static UserDadosRequest toDtoMed(final Medico medico){
        UserDadosRequest medicoDto = new UserDadosRequest();
        medicoDto.setEmail(medico.getEmail());
        medicoDto.setTelefone(medico.getTelefone());
        medicoDto.setSenha(medico.getSenha());
        medicoDto.setUrlImg(medico.getUrlImg());

        return medicoDto;
    }

    public static Medico toEntityMed(final UserDadosRequest dto){
        Medico medico = new Medico();
        medico.setEmail(dto.getEmail());
        medico.setTelefone(dto.getTelefone());
        medico.setSenha(dto.getSenha());
        medico.setUrlImg(dto.getUrlImg());

        return medico;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha != null && senha.isBlank() ? null : senha;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
