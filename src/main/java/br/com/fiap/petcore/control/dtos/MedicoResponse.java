package br.com.fiap.petcore.control.dtos;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.fiap.petcore.model.Exame;
import br.com.fiap.petcore.model.Medico;
import br.com.fiap.petcore.model.Prontuario;
import br.com.fiap.petcore.model.Receita;
import br.com.fiap.petcore.model.Relatorio;
import br.com.fiap.petcore.model.SexoEnum;

public class MedicoResponse {

    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private String telefone;
    private String email;
    private SexoEnum sexo;
    private String especialidade;
    private String urlImg;
    private Set<Long> idRelatorios;
    private Set<Long> idProntuarios;
    private Set<Long> idExames;
    private Set<Long> idReceitas;

    public static MedicoResponse toDto(final Medico medico) {
        MedicoResponse medicoDto = new MedicoResponse();

        medicoDto.setId(medico.getId());
        medicoDto.setNome(medico.getNome());
        medicoDto.setDataNascimento(medico.getDataNascimento());
        medicoDto.setTelefone(medico.getTelefone());
        medicoDto.setEmail(medico.getEmail());
        medicoDto.setSexo(medico.getSexo());
        medicoDto.setEspecialidade(medico.getEspecialidade());
        medicoDto.setUrlImg(medico.getUrlImg());

        medicoDto.setIdRelatorios(
                medico.getRelatorios()
                        .stream()
                        .map(Relatorio::getId)
                        .collect(Collectors.toSet())
        );

        medicoDto.setIdProntuarios(
                medico.getProntuarios()
                        .stream()
                        .map(Prontuario::getId)
                        .collect(Collectors.toSet())
        );

        medicoDto.setIdExames(
                medico.getExames()
                        .stream()
                        .map(Exame::getId)
                        .collect(Collectors.toSet())
        );

        medicoDto.setIdReceitas(
                medico.getReceitas()
                        .stream()
                        .map(Receita::getId)
                        .collect(Collectors.toSet())
        );

        return medicoDto;
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

    public Set<Long> getIdRelatorios() {
        return idRelatorios;
    }

    public void setIdRelatorios(Set<Long> idRelatorios) {
        this.idRelatorios = idRelatorios;
    }

    public Set<Long> getIdProntuarios() {
        return idProntuarios;
    }

    public void setIdProntuarios(Set<Long> idProntuarios) {
        this.idProntuarios = idProntuarios;
    }

    public Set<Long> getIdExames() {
        return idExames;
    }

    public void setIdExames(Set<Long> idExames) {
        this.idExames = idExames;
    }

    public Set<Long> getIdReceitas() {
        return idReceitas;
    }

    public void setIdReceitas(Set<Long> idReceitas) {
        this.idReceitas = idReceitas;
    }
}