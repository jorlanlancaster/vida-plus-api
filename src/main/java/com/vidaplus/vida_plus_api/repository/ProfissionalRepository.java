package com.vidaplus.vida_plus_api.repository;

import com.vidaplus.vida_plus_api.domain.Profissional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
    boolean existsByCpf(String cpf);
    Optional<Profissional> findByCpf(String cpf);
    Page<Profissional> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
