package com.clicare.sistema_clinica.service;

import com.clicare.sistema_clinica.model.Usuario; // Importe sua entidade User/Usuario
import com.clicare.sistema_clinica.model.Usuario;
import com.clicare.sistema_clinica.repository.UsuarioRepository; // Importe seu repositório de usuário
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * Esta classe implementa o "contrato" do Spring Security (UserDetailsService).
 * Sua única responsabilidade é ensinar ao Spring como encontrar um usuário
 * no nosso banco de dados a partir de um email.
 */
@Service // Marca esta classe como um componente de serviço do Spring
@RequiredArgsConstructor // Para injetar o UsuarioRepository via construtor
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Este é o único método exigido pelo contrato.
     * O Spring Security irá chamá-lo durante o processo de autenticação.
     *
     * @param email O email que o usuário digitou na tela de login.
     * @return um objeto UserDetails que o Spring Security entende.
     * @throws UsernameNotFoundException se o usuário não for encontrado no banco.
     */
    @Override
    @Transactional // É uma boa prática marcar como transacional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Usa o nosso UsuarioRepository para buscar o usuário no banco pelo email.
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário não encontrado com o email: " + email)
                );

        // 2. Converte o nosso objeto 'User' (do model) para um objeto 'UserDetails'
        //    que o Spring Security consegue entender.
        //    O construtor pede: username, password (hash), e uma lista de autorizações (roles).
        //    Por enquanto, a lista de autorizações está vazia.
        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getSenhaHash(),
                Collections.emptyList() // Futuramente, aqui iriam as "roles" (ex: "ROLE_PACIENTE")
        );
    }
}