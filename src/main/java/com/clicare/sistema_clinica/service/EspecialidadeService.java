package com.clicare.sistema_clinica.service;

import com.clicare.sistema_clinica.model.Especialidade;
import com.clicare.sistema_clinica.repository.EspecialidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspecialidadeService {

    private final EspecialidadeRepository especialidadeRepository;

    /**
     * Busca todas as especialidades cadastradas no banco de dados.
     * @return Uma lista de entidades Especialidade.
     */
    @Transactional(readOnly = true) // 'readOnly = true' otimiza a performance para consultas
    public List<Especialidade> listarTodas() {
        return especialidadeRepository.findAll();
    }
}