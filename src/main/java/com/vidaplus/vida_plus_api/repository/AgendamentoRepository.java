package com.vidaplus.vida_plus_api.repository;

import com.vidaplus.vida_plus_api.domain.Agendamento;
import com.vidaplus.vida_plus_api.domain.StatusAgendamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    // conflito: existe algum AGENDADO cujo intervalo se sobrepõe a [inicio, fim) para o mesmo profissional?
    boolean existsByProfissional_IdAndStatusAndInicioLessThanAndFimGreaterThan(
            Long profissionalId, StatusAgendamento status, LocalDateTime fim, LocalDateTime inicio);

    Page<Agendamento> findByProfissional_IdAndInicioBetween(
            Long profissionalId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Agendamento> findByPaciente_IdAndInicioBetween(
            Long pacienteId, LocalDateTime start, LocalDateTime end, Pageable pageable);
}
