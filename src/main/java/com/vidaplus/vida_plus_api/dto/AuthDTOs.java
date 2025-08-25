package com.vidaplus.vida_plus_api.dto;

import com.vidaplus.vida_plus_api.domain.Role;
import jakarta.validation.constraints.*;

public class AuthDTOs {
    public static record RegisterRequest(
            @NotBlank @Size(min=2,max=120) String nome,
            @NotBlank @Email String email,
            @NotBlank @Size(min=6,max=120) String senha,
            Role role // ADMIN ou PROFISSIONAL (opcional, default PROFISSIONAL)
    ) {}

    public static record LoginRequest(
            @NotBlank @Email String email,
            @NotBlank String senha
    ) {}

    public static record TokenResponse(String token) {}
}
