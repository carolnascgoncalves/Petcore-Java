package br.com.fiap.petcore.control.dtos;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.fiap.petcore.model.Exame;
import br.com.fiap.petcore.model.Historico;
import br.com.fiap.petcore.model.Prontuario;
import br.com.fiap.petcore.model.Receita;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProntuarioRequest {

    private LocalDateTime data;

    @NotEmpty(message = "A descrição é obrigatória")
    @Size(min = 5, max = 500, message = "A descrição deve ter entre 5 e 500 caracteres")
    private String descricao;

    @NotNull(message = "O histórico é obrigatório")
    private Long idHistorico;

    private Set<Long> idExames = new HashSet<>();
    private Set<Long> idReceitas = new HashSet<>();

    public static Prontuario toEntity(final ProntuarioRequest dto) {
        Prontuario prontuario = new Prontuario();
        Historico historico = new Historico();
        historico.setId(dto.getIdHistorico());

        prontuario.setData(LocalDateTime.now());
        prontuario.setDescricao(dto.getDescricao());
        prontuario.setHistorico(historico);

        if (dto.getIdReceitas() != null) {
            prontuario.setReceitas(dto.getIdReceitas().stream().map(id -> {
                Receita receita = new Receita();
                receita.setId(id);
                return receita;
            }).collect(Collectors.toSet()));
        } else {
            prontuario.setReceitas(new HashSet<>());
        }

        if (dto.getIdExames() != null) {
            prontuario.setExames(dto.getIdExames().stream().map(id -> {
                Exame exame = new Exame();
                exame.setId(id);
                return exame;
            }).collect(Collectors.toSet()));
        } else {
            prontuario.setExames(new HashSet<>());
        }

        return prontuario;
    }

    public static ProntuarioRequest toDto(final Prontuario prontuario) {
        ProntuarioRequest prontuarioDto = new ProntuarioRequest();
        prontuarioDto.setData(prontuario.getData());
        prontuarioDto.setDescricao(prontuario.getDescricao());
        prontuarioDto.setIdHistorico(prontuario.getHistorico().getId());
        prontuarioDto.setIdReceitas(prontuario.getReceitas().stream().map(Receita::getId).collect(Collectors.toSet()));
        prontuarioDto.setIdExames(prontuario.getExames().stream().map(Exame::getId).collect(Collectors.toSet()));
        return prontuarioDto;
    }

    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Long getIdHistorico() { return idHistorico; }
    public void setIdHistorico(Long idHistorico) { this.idHistorico = idHistorico; }
    public Set<Long> getIdExames() { return idExames; }
    public void setIdExames(Set<Long> idExames) { this.idExames = idExames; }
    public Set<Long> getIdReceitas() { return idReceitas; }
    public void setIdReceitas(Set<Long> idReceitas) { this.idReceitas = idReceitas; }
}
