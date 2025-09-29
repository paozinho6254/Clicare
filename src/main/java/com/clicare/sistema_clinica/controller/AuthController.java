package com.clicare.sistema_clinica.controller;

import com.clicare.sistema_clinica.dto.LoginRequestDTO;
import com.clicare.sistema_clinica.dto.LoginResponseDTO;
import com.clicare.sistema_clinica.dto.PacienteResponseDTO;
import com.clicare.sistema_clinica.dto.UsuarioResponseDTO;
import com.clicare.sistema_clinica.model.Usuario;
import com.clicare.sistema_clinica.repository.UsuarioRepository;
import com.clicare.sistema_clinica.service.JwtService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    // --- DEPENDÊNCIAS INJETADAS PELO CONSTRUTOR ---
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository; // <-- PEÇA ADICIONADA AQUI

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getSenha()
        );
        Authentication authentication = authenticationManager.authenticate(authToken);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> getAuthenticatedUser(Authentication authentication) {
        String userEmail = authentication.getName();

        // Agora a variável 'usuarioRepository' existe e pode ser usada
        Usuario usuario = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado, token inválido."));

        // Usei User/UserResponseDTO para consistência
        UsuarioResponseDTO responseDTO = UsuarioResponseDTO.fromEntity(usuario);

        return ResponseEntity.ok(responseDTO);
    }

}