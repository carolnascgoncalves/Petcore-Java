package br.com.fiap.petcore.control.dtos;

import java.time.LocalDate;

import br.com.fiap.petcore.model.Pet;
import br.com.fiap.petcore.model.SexoEnum;
import br.com.fiap.petcore.model.StatusEnum;

public class PetResponse {

    private Long id;
    private String nome;
    private int idade;
    private LocalDate dataNasc;
    private SexoEnum sexo;
    private String urlImg;
    private StatusEnum status;
    private String porte;
    private String especie;
    private String raca;
    private String pelagem;
    private Long idHistorico;

    public static PetResponse toDto(final Pet pet) {
        PetResponse petDto = new PetResponse();

        petDto.setId(pet.getId());
        petDto.setNome(pet.getNome());
        petDto.setIdade(pet.calcularIdade());
        petDto.setDataNasc(pet.getDataNascimento());
        petDto.setSexo(pet.getSexo());
        petDto.setUrlImg(pet.getUrlImg());
        petDto.setStatus(pet.getStatus());
        petDto.setPorte(pet.getPorte());
        petDto.setEspecie(pet.getEspecie());
        petDto.setRaca(pet.getRaca());
        petDto.setPelagem(pet.getPelagem());
        petDto.setIdHistorico(pet.getHistorico() != null ? pet.getHistorico().getId() : null);

        return petDto;
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

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
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

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
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

    public String getPelagem() {
        return pelagem;
    }

    public void setPelagem(String pelagem) {
        this.pelagem = pelagem;
    }

    public Long getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(Long idHistorico) {
        this.idHistorico = idHistorico;
    }
}