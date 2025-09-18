package com.clicare.sistema_clinica.dto;

import com.clicare.sistema_clinica.model.Agendamento;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AgendamentoRequestDTO {

    @NotNull(message = "O ID do paciente titular é obrigatório")
    private Integer idPacienteTitular;

    // Este campo é opcional, a consulta pode ser para o próprio titular
    private Integer idPacienteAtendido;

    @NotNull(message = "O ID do médico é obrigatório")
    private Integer idMedico;

    @NotNull(message = "O ID da clínica é obrigatório")
    private Integer idClinica;

    @NotNull(message = "O ID da especialidade é obrigatório")
    private Integer idEspecialidade;

    @NotNull(message = "O ID do horário na agenda é obrigatório")
    private Integer idHorarioAgenda;

    @NotNull(message = "A modalidade da consulta é obrigatória")
    private Agendamento.Modalidade modalidade;
}