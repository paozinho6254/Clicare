package com.clicare.sistema_clinica.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "estado")

public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer id;
    private String nome;
}