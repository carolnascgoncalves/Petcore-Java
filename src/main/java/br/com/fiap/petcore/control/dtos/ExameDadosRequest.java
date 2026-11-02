package br.com.fiap.petcore.control.dtos;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.fiap.petcore.model.Exame;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ExameDadosRequest {

    @NotEmpty(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotNull(message = "A data e hora são obrigatórias")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime data;

    @NotEmpty(message = "O tipo do exame é obrigatório")
    @Size(min = 5, max = 100, message = "O tipo do exame deve ter entre 5 e 100 caracteres")
    private String tipo;

    public static ExameDadosRequest toDto(final Exame exame) {
        ExameDadosRequest exameDto = new ExameDadosRequest();
        exameDto.setNome(exame.getNome());
        exameDto.setData(exame.getData());
        exameDto.setTipo(exame.getTipo());
        return exameDto;
    }

    public static Exame toEntity(final ExameDadosRequest dto) {
        Exame exame = new Exame();
        exame.setNome(dto.getNome());
        exame.setData(dto.getData());
        exame.setTipo(dto.getTipo());
        return exame;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
