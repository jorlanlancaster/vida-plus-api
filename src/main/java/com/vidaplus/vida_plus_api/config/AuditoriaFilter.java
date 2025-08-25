package com.vidaplus.vida_plus_api.config;

import com.vidaplus.vida_plus_api.domain.Auditoria;
import com.vidaplus.vida_plus_api.repository.AuditoriaRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

@Component
public class AuditoriaFilter extends OncePerRequestFilter {

    private final AuditoriaRepository repo;
    private static final Set<String> METHODS = Set.of("POST", "PUT", "DELETE");

    public AuditoriaFilter(AuditoriaRepository repo) {
        this.repo = repo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String method = request.getMethod();
        boolean shouldLog = METHODS.contains(method) || request.getRequestURI().startsWith("/auth/login");

        if (!shouldLog) {
            filterChain.doFilter(request, response);
            return;
        }

        // Executa a cadeia e depois lê o status
        filterChain.doFilter(request, response);

        Auditoria a = new Auditoria();
        a.setMetodo(method);
        a.setRota(request.getRequestURI());
        a.setStatus(response.getStatus());
        a.setUsuario(request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "anon");
        a.setIp(request.getRemoteAddr());
        a.setDataHora(LocalDateTime.now());
        repo.save(a);
    }
}
