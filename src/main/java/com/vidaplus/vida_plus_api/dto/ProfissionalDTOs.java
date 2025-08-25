package com.vidaplus.vida_plus_api.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class ProfissionalDTOs {

    public static record CreateProfissionalRequest(
            @NotBlank @Size(min = 2, max = 120) String nome,
            @NotBlank @Pattern(regexp = "\\d{11}") String cpf,
            @Size(max = 80) String conselho,
            @Size(max = 60) String especialidade,
            @Size(max = 20) String telefone,
            @Email @Size(max = 120) String email
    ) {}

    public static record UpdateProfissionalRequest(
            @NotBlank @Size(min = 2, max = 120) String nome,
            @Size(max = 80) String conselho,
            @Size(max = 60) String especialidade,
            @Size(max = 20) String telefone,
            @Email @Size(max = 120) String email
    ) {}

    public static record ProfissionalResponse(
            Long id,
            String nome,
            String cpf,
            String conselho,
            String especialidade,
            String telefone,
            String email,
            LocalDateTime criadoEm,
            LocalDateTime atualizadoEm
    ) {}
}
