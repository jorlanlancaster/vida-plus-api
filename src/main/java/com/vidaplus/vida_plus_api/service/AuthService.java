package com.vidaplus.vida_plus_api.service;

import com.vidaplus.vida_plus_api.domain.Role;
import com.vidaplus.vida_plus_api.domain.Usuario;
import com.vidaplus.vida_plus_api.dto.AuthDTOs.*;
import com.vidaplus.vida_plus_api.repository.UsuarioRepository;
import com.vidaplus.vida_plus_api.security.JwtUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository repo;

    public AuthService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public void register(RegisterRequest req) {
        if (repo.existsByEmail(req.email())) {
            throw new DataIntegrityViolationException("E-mail já cadastrado");
        }
        Usuario u = new Usuario();
        u.setNome(req.nome());
        u.setEmail(req.email());
        u.setSenhaHash(BCrypt.hashpw(req.senha(), BCrypt.gensalt()));
        u.setRole(req.role() != null ? req.role() : Role.PROFISSIONAL);
        repo.save(u);
    }

    public String login(LoginRequest req) {
        Usuario u = repo.findByEmail(req.email())
                .orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas"));
        if (!BCrypt.checkpw(req.senha(), u.getSenhaHash())) {
            throw new IllegalArgumentException("Credenciais inválidas");
        }
        return JwtUtil.generateToken(u.getEmail(), u.getRole().name());
    }
}
