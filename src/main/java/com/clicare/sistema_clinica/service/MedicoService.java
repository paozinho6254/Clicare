package com.clicare.sistema_clinica.service;

import com.clicare.sistema_clinica.dto.MedicoCadastroDTO;
import com.clicare.sistema_clinica.exception.RegraDeNegocioException;
import com.clicare.sistema_clinica.model.Medico;
import com.clicare.sistema_clinica.model.User;
import com.clicare.sistema_clinica.repository.MedicoRepository;
import com.clicare.sistema_clinica.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final UserRepository userRepository;
    private final MedicoRepository medicoRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Cadastra um novo médico no sistema.
     */
    @Transactional
    public Medico cadastrar(@Valid MedicoCadastroDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RegraDeNegocioException("O email informado já está em uso.");
        }
        if (medicoRepository.findByCrmAndCrmUf(dto.getCrm(), dto.getCrmUf()).isPresent()) {
            throw new RegraDeNegocioException("Este CRM já está cadastrado.");
        }

        User novoUsuario = User.builder()
                .name(dto.getNome())
                .email(dto.getEmail())
                .senhaHash(passwordEncoder.encode(dto.getSenha()))
                .typeUser(User.TypeUser.MEDICO)
                .ativo(true)
                .build();
        User usuarioSalvo = userRepository.save(novoUsuario);

        Medico novoMedico = Medico.builder()
                .usuario(usuarioSalvo)
                .crm(dto.getCrm())
                .crmUf(dto.getCrmUf())
                .build();

        return medicoRepository.save(novoMedico);
    }
}