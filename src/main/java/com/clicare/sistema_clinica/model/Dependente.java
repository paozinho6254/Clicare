package com.clicare.sistema_clinica.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "dependente")
public class Dependente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dependente")
    private Integer id;

    @Column(name = "nome_dependente", nullable = false)
    private String nome;

    @Column(name = "cpf_dependente", unique = true)
    private String cpf;

    // Vários dependentes pertencem a um paciente titular
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente pacienteTitular;
}