package com.clicare.sistema_clinica.controller;

import com.clicare.sistema_clinica.dto.MedicoCadastroDTO;
import com.clicare.sistema_clinica.model.Medico;
import com.clicare.sistema_clinica.service.MedicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody MedicoCadastroDTO dto) {
        Medico medicoSalvo = medicoService.cadastrar(dto);
        // Poderia retornar um MedicoResponseDTO aqui
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoSalvo);
    }
}