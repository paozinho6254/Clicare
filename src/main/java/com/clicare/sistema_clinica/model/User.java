package com.clicare.sistema_clinica.model;

import jakarta.persistence.*;
import lombok.*;

import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private TypeUser typeUser;

    @Column(nullable = false)
    private Boolean ativo;

    // Para Enuns colocar nas classes base
    public enum TypeUser {
        PACIENTE, MEDICO, CLINICA, ADMIN
    }

}
