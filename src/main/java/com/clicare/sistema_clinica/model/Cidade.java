package com.clicare.sistema_clinica.model;

import com.clicare.sistema_clinica.model.Estado;
import jakarta.persistence.*;
import lombok.Data;

// Cidade.java
@Data
@Entity
@Table(name = "cidade")
public class Cidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer id;
    private String nome;
    @ManyToOne @JoinColumn(name = "id_estado") private Estado estado;
}