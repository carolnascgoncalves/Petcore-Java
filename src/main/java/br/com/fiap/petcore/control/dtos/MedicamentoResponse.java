package br.com.fiap.petcore.control.dtos;

import br.com.fiap.petcore.model.Medicamento;

public class MedicamentoResponse {

    private Long id;
    private String nome;
    private String dosagem;

    public static MedicamentoResponse toDto(final Medicamento medicamento) {
        MedicamentoResponse medicamentoDto = new MedicamentoResponse();

        medicamentoDto.setId(medicamento.getId());
        medicamentoDto.setNome(medicamento.getNome());
        medicamentoDto.setDosagem(medicamento.getDosagem());

        return medicamentoDto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDosagem() { return dosagem; }
    public void setDosagem(String dosagem) { this.dosagem = dosagem; }
}
