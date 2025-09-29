package com.clicare.sistema_clinica.dto;

import com.clicare.sistema_clinica.model.Usuario;
import lombok.Data;

/**
 * DTO para retornar informações básicas e seguras do usuário autenticado.
 * Usado para verificar o status do login e exibir informações no header da página.
 */
@Data
public class UsuarioResponseDTO {

    private Integer id;
    private String nomeExibicao;
    private String email;
    private Usuario.TipoUsuario tipoUsuario;

    public static UsuarioResponseDTO fromEntity(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNomeExibicao(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTipoUsuario(usuario.getTipoUsuario());
        return dto;
    }
}