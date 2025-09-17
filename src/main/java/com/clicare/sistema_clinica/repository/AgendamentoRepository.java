package com.clicare.sistema_clinica.repository;

import com.clicare.sistema_clinica.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {

    // ===================================================================================
    // TÉCNICA 1: DERIVED QUERY METHODS (Consultas Derivadas do Nome do Método)
    // ===================================================================================

    /**
     * Encontra todos os agendamentos de um paciente específico (titular da conta).
     * O Spring navega pelo relacionamento: Agendamento -> Paciente -> ID.
     */
    List<Agendamento> findByPacienteTitularId(Integer idPaciente);

    /**
     * Encontra todos os agendamentos para um médico específico que estão em um determinado status.
     * Útil para ver, por exemplo, todos os agendamentos 'CONFIRMADO' de um médico.
     */
    List<Agendamento> findByMedicoIdAndStatus(Integer idMedico, Agendamento.StatusAgendamento status);

    /**
     * Encontra todos os agendamentos de uma clínica dentro de um intervalo de datas.
     * Perfeito para relatórios de faturamento ou produtividade.
     */
    List<Agendamento> findByClinicaIdAndHorarioDataHoraInicioBetween(Integer idClinica, LocalDateTime start, LocalDateTime end);


    // ===================================================================================
    // TÉCNICA 2: JPQL COM @Query (Para Consultas Mais Complexas)
    // ===================================================================================

    /**
     * Busca os próximos agendamentos de um médico a partir da data e hora atuais,
     * ordenando do mais próximo para o mais distante.
     * JPQL (Java Persistence Query Language) usa os nomes das classes e atributos Java, não das tabelas e colunas.
     *
     * @param medicoId O ID do médico a ser consultado.
     * @param dataAtual A data e hora a partir da qual a busca deve ser feita.
     * @return Uma lista de agendamentos futuros para o médico.
     */
    @Query("SELECT a FROM Agendamento a WHERE a.medico.id = :medicoId AND a.horario.dataHoraInicio >= :dataAtual ORDER BY a.horario.dataHoraInicio ASC")
    List<Agendamento> findProximosAgendamentosDoMedico(
            @Param("medicoId") Integer medicoId,
            @Param("dataAtual") LocalDateTime dataAtual
    );
}