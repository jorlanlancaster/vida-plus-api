package com.vidaplus.vida_plus_api.config;

import com.vidaplus.vida_plus_api.security.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/ping", "/auth/**").permitAll()
                .requestMatchers("/profissionais/**").hasRole("ADMIN")
                .requestMatchers("/auditoria/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        // Nosso filtro de JWT deve rodar antes da autenticação anônima
        http.addFilterBefore(new JwtFilter(), org.springframework.security.web.authentication.AnonymousAuthenticationFilter.class);

        return http.build();
    }

    static class JwtFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                throws ServletException, IOException {
            String header = request.getHeader("Authorization");
            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);
                try {
                    Claims claims = JwtUtil.parse(token);
                    String email = claims.getSubject();
                    String role = (String) claims.get("role"); // "ADMIN" ou "PROFISSIONAL"

                    var auth = new JwtAuth(email, role);
                    // *** PONTO CRÍTICO: informar ao Spring Security que o usuário está autenticado
                    SecurityContextHolder.getContext().setAuthentication(auth);

                } catch (Exception e) {
                    // token inválido/expirado → segue sem autenticar (endpoints protegidos darão 401)
                }
            }
            chain.doFilter(request, response);
        }
    }

    static class JwtAuth extends AbstractAuthenticationToken implements java.security.Principal {
        private final String email;
        JwtAuth(String email, String role) {
            super(List.of(new SimpleGrantedAuthority("ROLE_" + role))); // ex: ROLE_ADMIN
            this.email = email;
            setAuthenticated(true);
        }
        @Override public Object getCredentials() { return ""; }
        @Override public Object getPrincipal() { return email; }
        @Override public String getName() { return email; }
    }
}
