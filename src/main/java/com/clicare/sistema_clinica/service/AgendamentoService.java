package com.clicare.sistema_clinica.service;

import com.clicare.sistema_clinica.model.Agendamento;
import com.clicare.sistema_clinica.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor // Gera o construtor com os campos 'final'
public class AgendamentoService {

    // A dependência é injetada via construtor e é imutável
    private final AgendamentoRepository agendamentoRepository;

    // ... outros repositórios aqui

    /**
     * Serviço para obter a agenda do dia de um médico.
     */
    @Transactional(readOnly = true) // Transação apenas para leitura, mais performática
    public List<Agendamento> obterAgendaDoDiaParaMedico(Integer idMedico) {
        LocalDateTime inicioDoDia = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime fimDoDia = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);

        // Usando um dos métodos que criamos no repositório!
        return agendamentoRepository.findByClinicaIdAndHorarioDataHoraInicioBetween(idMedico, inicioDoDia, fimDoDia);
    }

    /**
     * Serviço para o dashboard do médico ver seus próximos compromissos.
     */
    @Transactional(readOnly = true)
    public List<Agendamento> obterProximosCompromissos(Integer idMedico) {
        // Usando nosso método com @Query!
        return agendamentoRepository.findProximosAgendamentosDoMedico(idMedico, LocalDateTime.now());
    }

    // ... outros métodos como criarAgendamento(), cancelarAgendamento(), etc.
}