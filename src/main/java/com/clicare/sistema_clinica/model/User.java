package com.clicare.sistema_clinica.model;

import com.clicare.sistema_clinica.enumeration.TypeUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
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

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private TypeUser typeUser;

    @Column(nullable = false)
    private Boolean active;
}
