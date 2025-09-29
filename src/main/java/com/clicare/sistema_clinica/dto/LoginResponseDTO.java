package com.clicare.sistema_clinica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // Construtor útil para criar o objeto
public class LoginResponseDTO {
    private String token;
}