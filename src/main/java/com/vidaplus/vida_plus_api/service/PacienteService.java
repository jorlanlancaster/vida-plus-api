package com.vidaplus.vida_plus_api.service;

import com.vidaplus.vida_plus_api.domain.Paciente;
import com.vidaplus.vida_plus_api.dto.PacienteDTOs.CreatePacienteRequest;
import com.vidaplus.vida_plus_api.dto.PacienteDTOs.PacienteResponse;
import com.vidaplus.vida_plus_api.repository.PacienteRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vidaplus.vida_plus_api.dto.PacienteDTOs.UpdatePacienteRequest;

@Service
public class PacienteService {


@Transactional(readOnly = true)
public PacienteResponse obter(Long id) {
    Paciente p = repo.findById(id).orElseThrow(() -> new com.vidaplus.vida_plus_api.web.NotFoundException("Paciente não encontrado"));
    return toResponse(p);
}

@Transactional
public PacienteResponse atualizar(Long id, UpdatePacienteRequest req) {
    Paciente p = repo.findById(id).orElseThrow(() -> new com.vidaplus.vida_plus_api.web.NotFoundException("Paciente não encontrado"));
    p.setNome(req.nome());
    p.setDataNascimento(req.dataNascimento());
    p.setTelefone(req.telefone());
    p.setEmail(req.email());
    Paciente salvo = repo.save(p);
    return toResponse(salvo);
}

@Transactional
public void excluir(Long id) {
    if (!repo.existsById(id)) {
        throw new com.vidaplus.vida_plus_api.web.NotFoundException("Paciente não encontrado");
    }
    repo.deleteById(id);
}







    public Page<PacienteResponse> listar(String nome, Pageable pageable) {
    Page<Paciente> page = (nome == null || nome.isBlank())
            ? repo.findAll(pageable)
            : repo.findByNomeContainingIgnoreCase(nome, pageable);

    return page.map(this::toResponse);
}

private PacienteResponse toResponse(Paciente p) {
    return new PacienteResponse(
            p.getId(), p.getNome(), p.getCpf(),
            p.getDataNascimento(), p.getTelefone(), p.getEmail(),
            p.getCriadoEm(), p.getAtualizadoEm()
    );
}




    private final PacienteRepository repo;

    public PacienteService(PacienteRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public PacienteResponse criar(CreatePacienteRequest req) {
        if (repo.existsByCpf(req.cpf())) {
            throw new DataIntegrityViolationException("CPF já cadastrado");
        }
        Paciente p = new Paciente();
        p.setNome(req.nome());
        p.setCpf(req.cpf());
        p.setDataNascimento(req.dataNascimento());
        p.setTelefone(req.telefone());
        p.setEmail(req.email());

        Paciente salvo = repo.save(p);
        return new PacienteResponse(
                salvo.getId(), salvo.getNome(), salvo.getCpf(),
                salvo.getDataNascimento(), salvo.getTelefone(), salvo.getEmail(),
                salvo.getCriadoEm(), salvo.getAtualizadoEm()
        );
    }
}
