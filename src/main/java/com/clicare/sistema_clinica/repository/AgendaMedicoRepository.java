package com.clicare.sistema_clinica.repository;

import com.clicare.sistema_clinica.model.AgendaMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgendaMedicoRepository extends JpaRepository<AgendaMedico, Integer> {

    /**
     * Encontra todos os horários de um médico em um determinado período e com um status específico.
     * Essencial para a funcionalidade "ver horários disponíveis".
     *
     * @param idMedico O ID do médico.
     * @param status O status do horário (ex: DISPONIVEL).
     * @param inicio O início do intervalo de busca.
     * @param fim O fim do intervalo de busca.
     * @return Uma lista de horários da agenda.
     */
    List<AgendaMedico> findByMedicoIdAndStatusAndDataHoraInicioBetween(Integer idMedico, AgendaMedico.StatusHorario status, LocalDateTime inicio, LocalDateTime fim);

    /**
     * Busca um horário específico na agenda de um médico em uma data/hora exata.
     * Usado para verificar se um horário específico já existe antes de criar um novo ou agendar.
     *
     * @param idMedico O ID do médico.
     * @param dataHoraInicio A data e hora exata do início do horário.
     * @return um Optional contendo o horário da agenda, se existir.
     */
    Optional<AgendaMedico> findByMedicoIdAndDataHoraInicio(Integer idMedico, LocalDateTime dataHoraInicio);
}