package com.clicare.sistema_clinica.service;

import com.clicare.sistema_clinica.model.AgendaMedico;
import com.clicare.sistema_clinica.model.Medico;
import com.clicare.sistema_clinica.repository.AgendaMedicoRepository;
import com.clicare.sistema_clinica.repository.MedicoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendaMedicoService {

    private final AgendaMedicoRepository agendaMedicoRepository;
    private final MedicoRepository medicoRepository;

    /**
     * Gera uma agenda de horários para um médico para os próximos 7 dias.
     * (Exemplo simplificado de uma lógica de negócio mais complexa)
     */
    public List<AgendaMedico> gerarAgendaSemanal(Integer idMedico) {
        Medico medico = medicoRepository.findById(idMedico)
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado."));

        List<AgendaMedico> novaAgenda = new ArrayList<>();
        LocalDate hoje = LocalDate.now();

        // Simulação de jornada de trabalho (Seg-Sex, 9h-18h, consultas de 1h)
        LocalTime horaInicio = LocalTime.of(9, 0);
        LocalTime horaFim = LocalTime.of(18, 0);
        int duracaoConsultaMinutos = 60;

        for (int i = 0; i < 7; i++) {
            LocalDate dia = hoje.plusDays(i);
            // Só gera agenda para dias de semana
            if (dia.getDayOfWeek() != DayOfWeek.SATURDAY && dia.getDayOfWeek() != DayOfWeek.SUNDAY) {

                LocalDateTime slot = LocalDateTime.of(dia, horaInicio);
                while (slot.toLocalTime().isBefore(horaFim)) {

                    // Regra: Só cria o horário se ele ainda não existir na agenda
                    if (agendaMedicoRepository.findByMedicoIdAndDataHoraInicio(idMedico, slot).isEmpty()) {
                        AgendaMedico novoHorario = AgendaMedico.builder()
                                .medico(medico)
                                .dataHoraInicio(slot)
                                .dataHoraFim(slot.plusMinutes(duracaoConsultaMinutos))
                                .status(AgendaMedico.StatusHorario.DISPONIVEL)
                                .build();
                        novaAgenda.add(novoHorario);
                    }
                    slot = slot.plusMinutes(duracaoConsultaMinutos);
                }
            }
        }
        return agendaMedicoRepository.saveAll(novaAgenda);
    }
}