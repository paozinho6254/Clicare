package com.clicare.sistema_clinica.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nome_usuario")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "senha_hash")
    private String senhaHash;

    @Column(name = "senha")
    private String senha;

    @Column(name = "tipo_usuario")
    private TypeUser typeUser;

    @Column(name = "ativo")
    private Boolean ativo;

    // Para Enuns colocar nas classes base
    public enum TypeUser {
        PACIENTE, MEDICO, CLINICA, ADMIN
    }

}
