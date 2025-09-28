package com.clicare.sistema_clinica.controller;

import com.clicare.sistema_clinica.model.Especialidade;
import com.clicare.sistema_clinica.service.EspecialidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@RequiredArgsConstructor
public class EspecialidadeController {

    private final EspecialidadeService especialidadeService;

    /**
     * Endpoint para listar todas as especialidades.
     * O frontend usará este endpoint para preencher o dropdown de especialidades.
     *
     * @return Uma resposta HTTP 200 OK com a lista de especialidades no corpo.
     */
    @GetMapping
    public ResponseEntity<List<Especialidade>> listarTodasAsEspecialidades() {
        List<Especialidade> especialidades = especialidadeService.listarTodas();
        return ResponseEntity.ok(especialidades);
    }
}