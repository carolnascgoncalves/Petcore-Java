package br.com.fiap.petcore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="receita_petcore")
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatorio")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 2 à 100 caracteres")
    private String nome;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(min = 5, max = 500, message = "A descrição deve ter entre 2 e 500 caracteres")
    private String descricao;

    @NotBlank(message = "A instrução é obrigatória")
    @Size(min = 5, max = 200, message = "A instrução deve ter entre 2 e 200 caracteres")
    private String instrucao;

    @NotNull(message = "A data é obrigatória")
    private LocalDateTime data;

    @DateTimeFormat(iso = ISO.DATE)
    @FutureOrPresent(message = "A validade não pode ser anterior à presente")
    private LocalDate validade;

    @ManyToOne
    @JoinColumn(name = "ID_med_(PK)")
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "ID_pront_(PK)")
    private Prontuario prontuario;

    @ManyToOne
    @JoinColumn(name = "ID_pet_(FK)")
    private Pet pet;

    @ManyToMany
    @JoinTable(name="rec_medic_petcore",
            joinColumns = @JoinColumn(name = "ID_rec_(FK)"),
            inverseJoinColumns = @JoinColumn(name="ID_medic_(FK)"))
    private Set<Medicamento> medicamentos;

    private boolean removida = false;

    public Receita() {}

    public Receita(Long id,String nome,String descricao,String instrucao,LocalDateTime data,LocalDate validade,Medico medico,Prontuario prontuario,Set<Medicamento> medicamentos) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.instrucao = instrucao;
        this.data = data;
        this.validade = validade;
        this.medico = medico;
        this.prontuario = prontuario;
        this.medicamentos = medicamentos;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
    public Pet getPet() { return pet; }
    public void setPet(Pet pet) { this.pet = pet; }
    public Prontuario getProntuario() { return prontuario; }
    public void setProntuario(Prontuario prontuario) { this.prontuario = prontuario; }
    public Set<Medicamento> getMedicamentos() { return medicamentos; }
    public void setMedicamentos(Set<Medicamento> medicamentos) { this.medicamentos = medicamentos; }
    public boolean isRemovida() { return removida; }
    public void setRemovida(boolean removida) { this.removida = removida; }

    public String getDataFormatada() {
        if (data == null) return "";
        return data.format(DateTimeFormatter.ofPattern("HH:mm'h' dd/MM/yyyy"));
    }

    public String getValidadeFormatada() {
        if (validade == null) return "";
        return validade.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
