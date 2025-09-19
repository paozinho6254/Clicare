package com.clicare.sistema_clinica.service;

import com.clicare.sistema_clinica.dto.PacienteCadastroRequestDTO;
import com.clicare.sistema_clinica.exception.RegraDeNegocioException;
import com.clicare.sistema_clinica.model.Pacient;
import com.clicare.sistema_clinica.model.Usuario;
import com.clicare.sistema_clinica.repository.PacientRepository;
import com.clicare.sistema_clinica.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PacientService {

    private final UsuarioRepository usuarioRepository;
    private final PacientRepository pacientRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Cadastra um novo paciente no sistema, criando o usuário e o perfil do paciente de forma transacional.
     */
    @Transactional // Garante que ou tudo é salvo com sucesso, ou nada é (rollback).
    public Pacient cadastrar(PacienteCadastroRequestDTO dto) {
        // --- VALIDAÇÃO DAS REGRAS DE NEGÓCIO ---
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RegraDeNegocioException("O email informado já está em uso.");
        }
        if (pacientRepository.existsByCpf(dto.getCpf())) {
            throw new RegraDeNegocioException("O CPF informado já está cadastrado.");
        }

        // --- EXECUÇÃO DA LÓGICA ---
        Usuario novoUsuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senhaHash(passwordEncoder.encode(dto.getSenha()))
                .senha(dto.getSenha())
                .typeUser(Usuario.TypeUser.PACIENTE)
                .ativo(true)
                .build();
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        Pacient novoPaciente = Pacient.builder()
                .usuario(usuarioSalvo)
                .cpf(dto.getCpf())
                .dataNascimento(dto.getDataNascimento())
                .build();

        return pacientRepository.save(novoPaciente);
    }

    /**
     * Busca um paciente pelo seu ID.
     */
    @Transactional(readOnly = true) // Otimização para operações de apenas leitura.
    public Pacient buscarPorId(Integer id) {
        return pacientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente com ID " + id + " não encontrado."));
    }
}