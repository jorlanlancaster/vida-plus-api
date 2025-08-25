package com.vidaplus.vida_plus_api.service;

import com.vidaplus.vida_plus_api.domain.*;
import com.vidaplus.vida_plus_api.dto.AgendamentoDTOs.*;
import com.vidaplus.vida_plus_api.repository.AgendamentoRepository;
import com.vidaplus.vida_plus_api.repository.PacienteRepository;
import com.vidaplus.vida_plus_api.repository.ProfissionalRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;

@Service
public class AgendamentoService {

    private final AgendamentoRepository repo;
    private final PacienteRepository pacienteRepo;
    private final ProfissionalRepository profissionalRepo;

    public AgendamentoService(AgendamentoRepository repo,
                              PacienteRepository pacienteRepo,
                              ProfissionalRepository profissionalRepo) {
        this.repo = repo;
        this.pacienteRepo = pacienteRepo;
        this.profissionalRepo = profissionalRepo;
    }

    @Transactional
    public AgendamentoResponse criar(CreateAgendamentoRequest req) {
        var paciente = pacienteRepo.findById(req.pacienteId())
                .orElseThrow(() -> new com.vidaplus.vida_plus_api.web.NotFoundException("Paciente não encontrado"));
        var profissional = profissionalRepo.findById(req.profissionalId())
                .orElseThrow(() -> new com.vidaplus.vida_plus_api.web.NotFoundException("Profissional não encontrado"));

        // validações de horário
        if (!req.inicio().isBefore(req.fim())) {
            throw new IllegalArgumentException("Horário inválido: 'inicio' deve ser antes de 'fim'");
        }
        long minutos = Duration.between(req.inicio(), req.fim()).toMinutes();
        if (minutos < 15 || minutos > 180) {
            throw new IllegalArgumentException("Duração deve ser entre 15 e 180 minutos");
        }
        if (req.inicio().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é permitido agendar no passado");
        }

        // conflito de agenda do mesmo profissional
        boolean conflito = repo.existsByProfissional_IdAndStatusAndInicioLessThanAndFimGreaterThan(
                profissional.getId(), StatusAgendamento.AGENDADO, req.fim(), req.inicio());
        if (conflito) {
            throw new DataIntegrityViolationException("Conflito de horário para este profissional");
        }

        var ag = new Agendamento();
        ag.setPaciente(paciente);
        ag.setProfissional(profissional);
        ag.setInicio(req.inicio());
        ag.setFim(req.fim());
        ag.setStatus(StatusAgendamento.AGENDADO);

        var salvo = repo.save(ag);
        return toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listar(FiltroQuery filtro, Pageable pageable) {
        LocalDate dia = filtro.data();
        LocalDateTime start = (dia != null) ? dia.atStartOfDay() : LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        Page<Agendamento> page;
        if (filtro.profissionalId() != null) {
            page = repo.findByProfissional_IdAndInicioBetween(filtro.profissionalId(), start, end, pageable);
        } else if (filtro.pacienteId() != null) {
            page = repo.findByPaciente_IdAndInicioBetween(filtro.pacienteId(), start, end, pageable);
        } else {
            // sem filtros: listar do dia
            page = repo.findAll(pageable);
        }
        return page.map(this::toResponse);
    }

    @Transactional
    public void cancelar(Long id) {
        var ag = repo.findById(id)
                .orElseThrow(() -> new com.vidaplus.vida_plus_api.web.NotFoundException("Agendamento não encontrado"));
        if (ag.getStatus() == StatusAgendamento.CANCELADO) return;
        ag.setStatus(StatusAgendamento.CANCELADO);
        repo.save(ag);
    }

    private AgendamentoResponse toResponse(Agendamento a) {
        return new AgendamentoResponse(
                a.getId(),
                a.getPaciente().getId(),
                a.getPaciente().getNome(),
                a.getProfissional().getId(),
                a.getProfissional().getNome(),
                a.getInicio(),
                a.getFim(),
                a.getStatus().name(),
                a.getCriadoEm(),
                a.getAtualizadoEm()
        );
    }
}
