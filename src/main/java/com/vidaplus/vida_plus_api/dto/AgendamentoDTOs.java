package com.vidaplus.vida_plus_api.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AgendamentoDTOs {

    public static record CreateAgendamentoRequest(
            @NotNull Long pacienteId,
            @NotNull Long profissionalId,
            @NotNull LocalDateTime inicio,
            @NotNull LocalDateTime fim
    ) {}

    public static record AgendamentoResponse(
            Long id,
            Long pacienteId,
            String pacienteNome,
            Long profissionalId,
            String profissionalNome,
            LocalDateTime inicio,
            LocalDateTime fim,
            String status,
            LocalDateTime criadoEm,
            LocalDateTime atualizadoEm
    ) {}

    // Filtro por dia/profissional/paciente
    public static record FiltroQuery(
            Long profissionalId,
            Long pacienteId,
            LocalDate data // dia (usaremos [00:00, 23:59:59])
    ) {}
}
