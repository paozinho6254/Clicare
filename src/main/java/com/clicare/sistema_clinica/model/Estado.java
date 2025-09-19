package com.clicare.sistema_clinica.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "estado")

public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Integer id;

    @Column(name = "none_estado")
    private String nome;
}