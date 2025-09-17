package com.clicare.sistema_clinica.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "paciente")
public class Pacient extends User{

    @Id
    @Column(name = "id_paciente")
    private Integer id;

    // Lazy carrega os dados de forma preguiçosa (lazy), ou seja ele não vai baixar e carregar
    // tudo de uma vez só, na verdade ele só devolve o básico
    @OneToOne(fetch = FetchType.LAZY)
    //MapsId indica para não gerar um novo id, mas sim, copiar o id da entidade abaixo
    @MapsId
    // sendo esta a coluna no banco que liga as duas tabelas
    /* No mySql

    id_paciente INT PRIMARY KEY
    FOREIGN KEY (id_paciente) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
     */
    @JoinColumn(name = "id_paciente")
    private User user;

    @Column(name = "cpf")
    private String cpf;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private Date dataNascimento;

    @Column(nullable = false)
    private String sexo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    // Um paciente titular pode ter vários dependentes
    @OneToMany(mappedBy = "pacienteTitular", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Dependente> dependentes;
}

