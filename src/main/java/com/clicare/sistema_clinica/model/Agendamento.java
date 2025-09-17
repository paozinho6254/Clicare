package com.clicare.sistema_clinica.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import com.clicare.sistema_clinica.model.Clinica;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "agendamento")
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agendamento")
    private Integer id;

    // Um agendamento ocupa um único slot na agenda do médico
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_horario_agenda", unique = true, nullable = false)
    private AgendaMedico horario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Pacient pacienteTitular; // Quem marcou

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dependente")
    private Dependente pacienteAtendido; // Quem será atendido (pode ser nulo)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_clinica", nullable = false)
    private Clinica clinica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_especialidade", nullable = false)
    private Especialidade especialidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAgendamento status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Modalidade modalidade;

    @Column(name = "link_teleconsulta")
    private String linkTeleconsulta;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @PrePersist // Define a data de criação antes de salvar
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    public enum StatusAgendamento {
        AGENDADO, CONFIRMADO, REALIZADO, CANCELADO, NAO_COMPARECEU
    }

    public enum Modalidade {
        PRESENCIAL, TELECONSULTA
    }

}