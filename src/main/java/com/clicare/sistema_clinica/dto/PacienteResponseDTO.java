package com.clicare.sistema_clinica.dto;

import com.clicare.sistema_clinica.model.Pacient;
import lombok.Data;
import java.util.Date;

@Data
public class PacienteResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private Date dataNascimento;

    // Construtor ou método estático para facilitar a conversão da Entidade para o DTO
    public static PacienteResponseDTO fromEntity(Pacient pacient) {
        PacienteResponseDTO dto = new PacienteResponseDTO();
        dto.setId(pacient.getId());
        dto.setCpf(pacient.getCpf());
        dto.setDataNascimento(pacient.getDataNascimento());
        // Busca os dados do usuário associado
        if (pacient.getUser() != null) {
            dto.setNome(pacient.getUser().getName());
            dto.setEmail(pacient.getUser().getEmail());
        }
        return dto;
    }
}