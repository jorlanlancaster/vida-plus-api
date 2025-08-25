package com.vidaplus.vida_plus_api.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PacienteDTOs {

    public static record CreatePacienteRequest(
            @NotBlank @Size(min = 2, max = 120) String nome,
            @NotBlank @Pattern(regexp = "\\d{11}") String cpf,
            @Past LocalDate dataNascimento,
            @Size(max = 20) String telefone,
            @Email @Size(max = 120) String email
    ) {}

    public static record UpdatePacienteRequest(
            @NotBlank @Size(min = 2, max = 120) String nome,
            @Past LocalDate dataNascimento,
            @Size(max = 20) String telefone,
            @Email @Size(max = 120) String email
    ) {}

    public static record PacienteResponse(
            Long id,
            String nome,
            String cpf,
            LocalDate dataNascimento,
            String telefone,
            String email,
            LocalDateTime criadoEm,
            LocalDateTime atualizadoEm
    ) {}
}
