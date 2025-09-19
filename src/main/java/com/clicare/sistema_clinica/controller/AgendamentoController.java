package com.clicare.sistema_clinica.controller;

import com.clicare.sistema_clinica.dto.AgendamentoRequestDTO;
import com.clicare.sistema_clinica.dto.AgendamentoResponseDTO;
import com.clicare.sistema_clinica.model.*;
import com.clicare.sistema_clinica.repository.*;
import com.clicare.sistema_clinica.service.AgendamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> criar(@Valid @RequestBody AgendamentoRequestDTO dto) {
        Agendamento agendamentoCriado = agendamentoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(AgendamentoResponseDTO.fromEntity(agendamentoCriado));
    }

    @PatchMapping("/{id}/cancelar") // PATCH é bom para atualizações parciais como mudar um status
    public ResponseEntity<AgendamentoResponseDTO> cancelar(@PathVariable Integer id) {
        Agendamento agendamentoCancelado = agendamentoService.cancelar(id);
        return ResponseEntity.ok(AgendamentoResponseDTO.fromEntity(agendamentoCancelado));
    }
}