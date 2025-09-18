package com.clicare.sistema_clinica.dto;

import com.clicare.sistema_clinica.model.Agendamento;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AgendamentoResponseDTO {

    private Integer idAgendamento;
    private LocalDateTime dataHoraInicio;
    private String status;
    private String modalidade;
    private String nomePacienteAtendido;
    private String nomeMedico;
    private String nomeClinica;
    private String nomeEspecialidade;

    /**
     * Este é um método "mapper". A camada de Serviço será responsável
     * por chamar este método e construir o DTO, pois ela tem acesso
     * a todas as entidades carregadas.
     */
    public static AgendamentoResponseDTO fromEntity(Agendamento agendamento) {
        AgendamentoResponseDTO dto = new AgendamentoResponseDTO();
        dto.setIdAgendamento(agendamento.getId());
        dto.setStatus(agendamento.getStatus().name());
        dto.setModalidade(agendamento.getModalidade().name());

        // Pega os dados dos relacionamentos
        if (agendamento.getHorario() != null) {
            dto.setDataHoraInicio(agendamento.getHorario().getDataHoraInicio());
        }
        if (agendamento.getMedico() != null && agendamento.getMedico().getUsuario() != null) {
            dto.setNomeMedico(agendamento.getMedico().getUsuario().getName());
        }
        if (agendamento.getClinica() != null && agendamento.getClinica().getUsuario() != null) {
            dto.setNomeClinica(agendamento.getClinica().getUsuario().getName());
        }
        if (agendamento.getEspecialidade() != null) {
            dto.setNomeEspecialidade(agendamento.getEspecialidade().getNome());
        }

        // Lógica para definir o nome do paciente que será atendido
        if (agendamento.getPacienteAtendido() != null) {
            dto.setNomePacienteAtendido(agendamento.getPacienteAtendido().getNome());
        } else if (agendamento.getPacienteTitular() != null && agendamento.getPacienteTitular().getUser() != null) {
            dto.setNomePacienteAtendido(agendamento.getPacienteTitular().getUser().getName());
        }

        return dto;
    }
}