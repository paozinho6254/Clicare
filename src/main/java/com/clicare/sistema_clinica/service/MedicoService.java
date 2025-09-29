package com.clicare.sistema_clinica.service;

import com.clicare.sistema_clinica.dto.MedicoCadastroDTO;
import com.clicare.sistema_clinica.exception.RegraDeNegocioException;
import com.clicare.sistema_clinica.model.Medico;
import com.clicare.sistema_clinica.model.Usuario;
import com.clicare.sistema_clinica.repository.MedicoRepository;
import com.clicare.sistema_clinica.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//@RequiredArgsConstructor
public class MedicoService {

    private final UsuarioRepository usuarioRepository;
    private final MedicoRepository medicoRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Cadastra um novo médico no sistema.
     */

    public MedicoService(MedicoRepository medicoRepository,
                         UsuarioRepository usuarioRepository,
                         @Lazy PasswordEncoder passwordEncoder) { // <-- Tente anotar o parâmetro aqui
        this.medicoRepository = medicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Medico cadastrar(@Valid MedicoCadastroDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RegraDeNegocioException("O email informado já está em uso.");
        }
        if (medicoRepository.findByCrmAndCrmUf(dto.getCrm(), dto.getCrmUf()).isPresent()) {
            throw new RegraDeNegocioException("Este CRM já está cadastrado.");
        }

        Usuario novoUsuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senhaHash(passwordEncoder.encode(dto.getSenha()))
                .senha(dto.getSenha())
                .typeUser(Usuario.TypeUser.MEDICO)
                .ativo(true)
                .build();
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        Medico novoMedico = Medico.builder()
                .usuario(usuarioSalvo)
                .crm(dto.getCrm())
                .crmUf(dto.getCrmUf())
                .build();

        return medicoRepository.save(novoMedico);
    }
}