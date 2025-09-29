package com.clicare.sistema_clinica.controller;

import com.clicare.sistema_clinica.dto.LoginRequestDTO;
import com.clicare.sistema_clinica.dto.LoginResponseDTO;
import com.clicare.sistema_clinica.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    // O "gatekeeper" do Spring Security que orquestra a autenticação.
    private final AuthenticationManager authenticationManager;

    // Nosso serviço customizado para criar os tokens JWT.
    private final JwtService jwtService;

    /**
     * Endpoint para autenticar um usuário e retornar um token JWT.
     * Este é o método que o formulário de login da sua tela chama.
     *
     * @param loginRequest DTO contendo o email e a senha do usuário.
     * @return Um ResponseEntity com o token JWT em caso de sucesso.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {

        // 1. CRIAR O OBJETO DE AUTENTICAÇÃO
        //    Criamos um "pacote" com as credenciais que o usuário enviou (email e senha em texto puro).
        //    Este objeto ainda não está autenticado.
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getSenha()
        );

        // 2. EXECUTAR A AUTENTICAÇÃO
        //    Passamos o pacote de credenciais para o AuthenticationManager.
        //    Ele fará todo o trabalho pesado nos bastidores:
        //      a. Chama o seu UserDetailsServiceImpl para buscar o usuário pelo email.
        //      b. Pega a senha criptografada que veio do banco.
        //      c. Usa o PasswordEncoder para comparar a senha enviada com a senha do banco.
        //      d. Se tudo der certo, retorna um objeto 'Authentication' completo e autenticado.
        //      e. Se as credenciais estiverem erradas, ele lança uma exceção (que o Spring captura e retorna um erro 401/403).
        Authentication authentication = authenticationManager.authenticate(authToken);

        // 3. GERAR O TOKEN JWT
        //    Se chegamos até aqui, o login foi um sucesso.
        //    Pegamos os detalhes do usuário autenticado (que agora estão no objeto 'authentication').
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        //    Usamos nosso JwtService para criar um token baseado nos detalhes do usuário.
        String token = jwtService.generateToken(userDetails);

        // 4. RETORNAR A RESPOSTA
        //    Criamos nosso DTO de resposta com o token e o enviamos de volta para o frontend
        //    com um status HTTP 200 OK.
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    // Você também terá o endpoint GET /me aqui para validar o token.
}