package br.com.fiap.petcore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Entity
@Table(name="relatorio_petcore")
public class Relatorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, max = 500, message = "A observacao deve ter no maximo 500 caracteres")
    private String observacao;

    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "ID_hist_(PK)")
    private Historico historico;

    @ManyToOne
    @JoinColumn(name = "ID_med_(PK)")
    private Medico medico;

    @ManyToMany(mappedBy = "relatorios", fetch = FetchType.LAZY)
    private Set<Clinica> clinicas;

    public Relatorio() {}

    public Relatorio(Long id,String observacao,LocalDateTime data,Historico historico,Medico medico,Set<Clinica> clinicas) {
        this.id = id;
        this.observacao = observacao;
        this.data = data;
        this.historico = historico;
        this.medico = medico;
        this.clinicas = clinicas;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
    public Historico getHistorico() { return historico; }
    public void setHistorico(Historico historico) { this.historico = historico; }
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
    public Set<Clinica> getClinicas() { return clinicas; }
    public void setClinicas(Set<Clinica> clinicas) { this.clinicas = clinicas; }

    public String getDataFormatada() {
        if (data == null) return "";
        return data.format(DateTimeFormatter.ofPattern("HH:mm'h' dd/MM/yyyy"));
    }
}
