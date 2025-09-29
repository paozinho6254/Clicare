package com.clicare.sistema_clinica.service;

import com.clicare.sistema_clinica.dto.AgendamentoRequestDTO;
import com.clicare.sistema_clinica.exception.RegraDeNegocioException;
import com.clicare.sistema_clinica.model.*;
import com.clicare.sistema_clinica.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final AgendaMedicoRepository agendaMedicoRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final ClinicaRepository clinicaRepository;
    private final EspecialidadeRepository especialidadeRepository;
    private final DependenteRepository dependenteRepository; // Supondo que você criou este repositório

    /**
     * Cria um novo agendamento no sistema.
     * Este método é chamado pelo AgendamentoController.
     *
     * @param dto Os dados da requisição para criar o agendamento.
     * @return A entidade Agendamento que foi criada e salva.
     */
    @Transactional
    public Agendamento criar(AgendamentoRequestDTO dto) {
        // --- 1. BUSCA E VALIDAÇÃO DAS ENTIDADES NECESSÁRIAS ---
        Paciente pacienteTitular = pacienteRepository.findById(dto.getIdPacienteTitular())
                .orElseThrow(() -> new EntityNotFoundException("Paciente com ID " + dto.getIdPacienteTitular() + " não encontrado."));

        Medico medico = medicoRepository.findById(dto.getIdMedico())
                .orElseThrow(() -> new EntityNotFoundException("Médico com ID " + dto.getIdMedico() + " não encontrado."));

        Clinica clinica = clinicaRepository.findById(dto.getIdClinica())
                .orElseThrow(() -> new EntityNotFoundException("Clínica com ID " + dto.getIdClinica() + " não encontrada."));

        Especialidade especialidade = especialidadeRepository.findById(dto.getIdEspecialidade())
                .orElseThrow(() -> new EntityNotFoundException("Especialidade com ID " + dto.getIdEspecialidade() + " não encontrada."));

        AgendaMedico horario = agendaMedicoRepository.findById(dto.getIdHorarioAgenda())
                .orElseThrow(() -> new EntityNotFoundException("Horário com ID " + dto.getIdHorarioAgenda() + " não encontrado na agenda."));

        Dependente pacienteAtendido = null;
        if (dto.getIdPacienteAtendido() != null) {
            pacienteAtendido = dependenteRepository.findById(dto.getIdPacienteAtendido())
                    .orElseThrow(() -> new EntityNotFoundException("Dependente com ID " + dto.getIdPacienteAtendido() + " não encontrado."));

            // Regra de Negócio: Garante que o dependente pertence ao paciente titular que está agendando.
            if (!pacienteAtendido.getPacienteTitular().getId().equals(pacienteTitular.getId())) {
                throw new RegraDeNegocioException("Acesso negado: O dependente não pertence ao titular da conta.");
            }
        }

        // --- 2. APLICAÇÃO DAS REGRAS DE NEGÓCIO CRÍTICAS ---
        if (horario.getStatus() != AgendaMedico.StatusHorario.DISPONIVEL) {
            throw new RegraDeNegocioException("O horário selecionado não está mais disponível.");
        }
        if (!horario.getMedico().getId().equals(medico.getId())) {
            throw new RegraDeNegocioException("Inconsistência de dados: O horário selecionado não pertence ao médico informado.");
        }

        // --- 3. EXECUÇÃO DA LÓGICA (OPERAÇÕES DE ESCRITA) ---
        // Altera o estado do sistema: o horário agora está ocupado.
        horario.setStatus(AgendaMedico.StatusHorario.AGENDADO);

        // Cria a nova entidade de Agendamento com os dados validados.
        Agendamento novoAgendamento = Agendamento.builder()
                .pacienteTitular(pacienteTitular)
                .pacienteAtendido(pacienteAtendido) // Será nulo se a consulta for para o titular
                .medico(medico)
                .clinica(clinica)
                .especialidade(especialidade)
                .horario(horario)
                .status(Agendamento.StatusAgendamento.AGENDADO)
                .modalidade(dto.getModalidade())
                .build();

        // Salva o novo agendamento. A alteração no 'horario' também será persistida
        // automaticamente pelo Hibernate por estar dentro da mesma transação.
        return agendamentoRepository.save(novoAgendamento);
    }

    /**
     * Cancela um agendamento existente e libera o horário correspondente na agenda do médico.
     * Este método é chamado pelo AgendamentoController.
     *
     * @param idAgendamento O ID do agendamento que deve ser cancelado.
     * @return A entidade Agendamento com o status atualizado para CANCELADO.
     */
    @Transactional
    public Agendamento cancelar(Integer idAgendamento) {
        Agendamento agendamento = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento com ID " + idAgendamento + " não encontrado."));

        // Regra de Negócio: Não é possível cancelar um agendamento que já ocorreu ou já foi cancelado.
        if (agendamento.getStatus() == Agendamento.StatusAgendamento.REALIZADO ||
                agendamento.getStatus() == Agendamento.StatusAgendamento.CANCELADO ||
                agendamento.getStatus() == Agendamento.StatusAgendamento.NAO_COMPARECEU) {
            throw new RegraDeNegocioException("Este agendamento está em um status que não permite cancelamento.");
        }

        // Altera o estado das entidades
        agendamento.setStatus(Agendamento.StatusAgendamento.CANCELADO);
        agendamento.getHorario().setStatus(AgendaMedico.StatusHorario.DISPONIVEL); // Libera o slot na agenda

        // O Spring Data JPA/Hibernate detecta as alterações nas entidades gerenciadas
        // e as salva no banco de dados ao final da transação.
        return agendamento;
    }
}