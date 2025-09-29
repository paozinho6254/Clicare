package com.clicare.sistema_clinica.dto;

import com.clicare.sistema_clinica.model.Especialidade;
import com.clicare.sistema_clinica.model.Medico;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO para retornar o perfil completo e detalhado de um Médico.
 */
@Data
public class MedicoResponseDTO {

    // Dados que vêm da entidade User associada
    private Integer id;
    private String nome;
    private String email;

    // Dados específicos da entidade Medico
    private String crm;
    private String crmUf;
    private String telefone;
    private Set<String> especialidades; // Retorna apenas os nomes das especialidades

    /**
     * Método "mapper" para converter uma entidade Medico neste DTO.
     * @param medico A entidade Medico vinda do banco de dados.
     * @return um DTO com os dados completos do perfil do médico.
     */
    public static MedicoResponseDTO fromEntity(Medico medico) {
        if (medico == null) {
            return null;
        }
        MedicoResponseDTO dto = new MedicoResponseDTO();

        // Dados específicos do médico
        dto.setCrm(medico.getCrm());
        dto.setCrmUf(medico.getCrmUf());
        dto.setTelefone(medico.getTelefone());

        // Mapeia a lista de entidades Especialidade para uma lista de Strings (nomes)
        if (medico.getEspecialidades() != null) {
            dto.setEspecialidades(
                    medico.getEspecialidades().stream()
                            .map(Especialidade::getNome)
                            .collect(Collectors.toSet())
            );
        }

        // Dados da entidade User associada
        if (medico.getUsuario() != null) {
            dto.setId(medico.getUsuario().getId());
            dto.setNome(medico.getUsuario().getNome());
            dto.setEmail(medico.getUsuario().getEmail());
        }

        return dto;
    }
}