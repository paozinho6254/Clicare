package com.clicare.sistema_clinica.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;


@Entity
@NoArgsConstructor // <-- GERA O CONSTRUTOR OBRIGATÓRIO PARA O JPA
@AllArgsConstructor // <-- GERA UM CONSTRUTOR ÚTIL COM TODOS OS ARGUMENTOS
@Builder
@Getter
@Setter
@Table(name = "paciente")
public class Paciente {

    @Id
    @Column(name = "id_paciente")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // Diz que o ID desta entidade vem da relação abaixo
    @JoinColumn(name = "id_paciente") // Define o nome da coluna que faz a ligação
    private Usuario usuario;


    @Column(name = "cpf_paciente")
    private String cpf;

    @Column(name = "telefone_paciente")
    private String telefone;

    @Column(name = "data_nascimento_paciente")
    private LocalDate dataNascimento;

    @Column(name = "sexo_paciente")
    private String sexo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    // Um paciente titular pode ter vários dependentes
    @OneToMany(mappedBy = "pacienteTitular", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Dependente> dependentes;
}

