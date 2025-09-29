package com.clicare.sistema_clinica.controller;

import com.clicare.sistema_clinica.dto.PacienteCadastroRequestDTO;
import com.clicare.sistema_clinica.dto.PacienteResponseDTO;
import com.clicare.sistema_clinica.model.Paciente;
import com.clicare.sistema_clinica.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping("/status")
    public String verificarStatus() {
        return "API da Clínica no ar! Tudo funcionando.";
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<PacienteResponseDTO> cadastrar(@Valid @RequestBody PacienteCadastroRequestDTO dto) {
        Paciente pacienteSalvo = pacienteService.cadastrar(dto);
        // Converte a entidade salva para um DTO de resposta antes de enviar
        return ResponseEntity.status(HttpStatus.CREATED).body(PacienteResponseDTO.fromEntity(pacienteSalvo));
    }

}