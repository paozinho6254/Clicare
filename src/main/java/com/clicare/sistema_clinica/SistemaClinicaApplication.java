package com.clicare.sistema_clinica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan; // <-- IMPORTE A ANOTAÇÃO

@SpringBootApplication
public class SistemaClinicaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaClinicaApplication.class, args);
    }

}