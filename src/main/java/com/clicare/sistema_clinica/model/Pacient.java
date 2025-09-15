package com.clicare.sistema_clinica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Entity
@Getter
@Setter
public class Pacient extends User{

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private Date dataNascimento;

    @Column(nullable = false)
    private String sexo;

    @Column(nullable = false)
    ;

}
