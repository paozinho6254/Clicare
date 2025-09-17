package com.clicare.sistema_clinica.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

// Endereco.java
@Data @Entity @Table(name = "endereco")
public class Endereco {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer id;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cep;
    @ManyToOne @JoinColumn(name = "id_cidade") private Cidade cidade;
}