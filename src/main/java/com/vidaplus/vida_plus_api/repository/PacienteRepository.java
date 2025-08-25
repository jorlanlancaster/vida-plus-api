package com.vidaplus.vida_plus_api.repository;

import com.vidaplus.vida_plus_api.domain.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    boolean existsByCpf(String cpf);

    Optional<Paciente> findByCpf(String cpf);

    Page<Paciente> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
