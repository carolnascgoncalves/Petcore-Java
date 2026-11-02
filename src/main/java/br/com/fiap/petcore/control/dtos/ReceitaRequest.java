package br.com.fiap.petcore.control.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.fiap.petcore.model.Medicamento;
import br.com.fiap.petcore.model.Pet;
import br.com.fiap.petcore.model.Receita;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReceitaRequest {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(min = 5, max = 500, message = "A descrição deve ter entre 5 e 500 caracteres")
    private String descricao;

    @NotBlank(message = "A instrução é obrigatória")
    @Size(min = 5, max = 200, message = "A instrução deve ter entre 5 e 200 caracteres")
    private String instrucao;

    private LocalDateTime data;

    @NotNull(message = "A validade é obrigatória")
    @FutureOrPresent(message = "A validade não pode ser anterior à data atual")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate validade;

    @NotNull(message = "O pet é obrigatório")
    private Long idPet;

    @NotNull(message = "A quantidade de medicamentos é obrigatória")
    private Integer quantidadeMedicamentos;

    private List<Long> idMedicamentos;

    public static Receita toEntity(final ReceitaRequest dto) {
        Receita receita = new Receita();
        receita.setNome(dto.getNome());
        receita.setDescricao(dto.getDescricao());
        receita.setInstrucao(dto.getInstrucao());
        receita.setData(LocalDateTime.now());
        receita.setValidade(dto.getValidade());
        Pet pet = new Pet();
        pet.setId(dto.getIdPet());
        receita.setPet(pet);

        Set<Medicamento> medicamentos = new HashSet<>();

        if (dto.getIdMedicamentos() != null) {
            for (Long id : dto.getIdMedicamentos()) {
                Medicamento medicamento = new Medicamento();
                medicamento.setId(id);
                medicamentos.add(medicamento);
            }
        }

        receita.setMedicamentos(medicamentos);
        return receita;
    }

    public static ReceitaRequest toDto(final Receita receita) {
        ReceitaRequest receitaDto = new ReceitaRequest();
        receitaDto.setNome(receita.getNome());
        receitaDto.setDescricao(receita.getDescricao());
        receitaDto.setInstrucao(receita.getInstrucao());
        receitaDto.setData(receita.getData());
        receitaDto.setValidade(receita.getValidade());
        receitaDto.setIdPet(receita.getPet().getId());
        receitaDto.setIdMedicamentos(receita.getMedicamentos().stream().map(Medicamento::getId).collect(Collectors.toCollection(ArrayList::new)));
        return receitaDto;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getInstrucao() { return instrucao; }
    public void setInstrucao(String instrucao) { this.instrucao = instrucao; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
    public LocalDate getValidade() { return validade; }
    public void setValidade(LocalDate validade) { this.validade = validade; }
    public Long getIdPet() { return idPet; }
    public void setIdPet(Long idPet) { this.idPet = idPet; }
    public Integer getQuantidadeMedicamentos() { return quantidadeMedicamentos; }
    public void setQuantidadeMedicamentos(Integer quantidadeMedicamentos) { this.quantidadeMedicamentos = quantidadeMedicamentos; }
    public List<Long> getIdMedicamentos() { return idMedicamentos; }
    public void setIdMedicamentos(List<Long> idMedicamentos) { this.idMedicamentos = idMedicamentos; }
}
