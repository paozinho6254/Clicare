package com.clicare.sistema_clinica.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "medico")
public class Medico {

    @Id
    @Column(name = "id_medico")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // Diz que o ID desta entidade vem da relação abaixo
    @JoinColumn(name = "id_medico") // Define o nome da coluna que faz a ligação
    private Usuario usuario;

    @Column(name = "crm_medico", nullable = false)
    private String crm;

    @Column(name = "crm_uf", nullable = false, length = 2)
    private String crmUf;

    @Column(name = "telefone_medico")
    private String telefone;

    // Lado "dono" da relação N-N com Clinica
    @ManyToMany
    @JoinTable(
            name = "medico_clinica",
            joinColumns = @JoinColumn(name = "id_medico"),
            inverseJoinColumns = @JoinColumn(name = "id_clinica")
    )
    private Set<Clinica> clinicas;

    // Lado "dono" da relação N-N com Especialidade
    @ManyToMany
    @JoinTable(
            name = "medico_especialidade",
            joinColumns = @JoinColumn(name = "id_medico"),
            inverseJoinColumns = @JoinColumn(name = "id_especialidade")
    )
    private Set<Especialidade> especialidades;
}