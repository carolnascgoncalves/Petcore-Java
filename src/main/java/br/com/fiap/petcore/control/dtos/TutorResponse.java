package br.com.fiap.petcore.control.dtos;


import java.time.LocalDate;

import br.com.fiap.petcore.model.SexoEnum;
import br.com.fiap.petcore.model.Tutor;

public class TutorResponse {

    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private String telefone;
    private String email;
    private SexoEnum sexo;
    private String urlImg;

    public static TutorResponse toDto(final Tutor tutor) {
        TutorResponse tutorDto = new TutorResponse();

        tutorDto.setId(tutor.getId());
        tutorDto.setNome(tutor.getNome());
        tutorDto.setDataNascimento(tutor.getDataNascimento());
        tutorDto.setTelefone(tutor.getTelefone());
        tutorDto.setEmail(tutor.getEmail());
        tutorDto.setSexo(tutor.getSexo());
        tutorDto.setUrlImg(tutor.getUrlImg());

        return tutorDto;
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

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}