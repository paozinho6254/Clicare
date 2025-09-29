package com.clicare.sistema_clinica.dto;

import com.clicare.sistema_clinica.model.Paciente;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO para retornar o perfil completo e detalhado de um Paciente.
 * Usado em telas como "Meus Dados" ou ao consultar informações de um paciente.
 */
@Data
public class PacienteResponseDTO {

    // Dados que vêm da entidade User associada
    private Integer id;
    private String nome;
    private String email;

    // Dados específicos da entidade Paciente
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private String sexo;
    // Futuramente, poderia incluir informações de endereço, dependentes, etc.

    /**
     * Método "mapper" para converter uma entidade Patient neste DTO.
     * Ele "achata" a estrutura, combinando dados de Patient e do User associado.
     * @param paciente A entidade Patient vinda do banco de dados.
     * @return um DTO com os dados completos do perfil do paciente.
     */
    public static PacienteResponseDTO fromEntity(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        PacienteResponseDTO dto = new PacienteResponseDTO();

        // Preenche com os dados específicos do paciente
        dto.setCpf(paciente.getCpf());
        dto.setDataNascimento(paciente.getDataNascimento());
        dto.setTelefone(paciente.getTelefone());
        dto.setSexo(paciente.getSexo());

        // Preenche com os dados da entidade User associada
        if (paciente.getUsuario() != null) {
            dto.setId(paciente.getUsuario().getId());
            dto.setNome(paciente.getUsuario().getNome());
            dto.setEmail(paciente.getUsuario().getEmail());
        }

        return dto;
    }
}