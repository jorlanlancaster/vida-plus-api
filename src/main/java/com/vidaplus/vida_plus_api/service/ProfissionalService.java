package com.vidaplus.vida_plus_api.service;

import com.vidaplus.vida_plus_api.domain.Profissional;
import com.vidaplus.vida_plus_api.dto.ProfissionalDTOs.*;
import com.vidaplus.vida_plus_api.repository.ProfissionalRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfissionalService {

    private final ProfissionalRepository repo;

    public ProfissionalService(ProfissionalRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public ProfissionalResponse criar(CreateProfissionalRequest req) {
        if (repo.existsByCpf(req.cpf())) {
            throw new DataIntegrityViolationException("CPF já cadastrado");
        }
        Profissional p = new Profissional();
        p.setNome(req.nome());
        p.setCpf(req.cpf());
        p.setConselho(req.conselho());
        p.setEspecialidade(req.especialidade());
        p.setTelefone(req.telefone());
        p.setEmail(req.email());
        Profissional salvo = repo.save(p);
        return toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public Page<ProfissionalResponse> listar(String nome, Pageable pageable) {
        var page = (nome == null || nome.isBlank())
                ? repo.findAll(pageable)
                : repo.findByNomeContainingIgnoreCase(nome, pageable);
        return page.map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public ProfissionalResponse obter(Long id) {
        var p = repo.findById(id).orElseThrow(() ->
                new com.vidaplus.vida_plus_api.web.NotFoundException("Profissional não encontrado"));
        return toResponse(p);
    }

    @Transactional
    public ProfissionalResponse atualizar(Long id, UpdateProfissionalRequest req) {
        var p = repo.findById(id).orElseThrow(() ->
                new com.vidaplus.vida_plus_api.web.NotFoundException("Profissional não encontrado"));
        p.setNome(req.nome());
        p.setConselho(req.conselho());
        p.setEspecialidade(req.especialidade());
        p.setTelefone(req.telefone());
        p.setEmail(req.email());
        return toResponse(repo.save(p));
    }

    @Transactional
    public void excluir(Long id) {
        if (!repo.existsById(id)) {
            throw new com.vidaplus.vida_plus_api.web.NotFoundException("Profissional não encontrado");
        }
        repo.deleteById(id);
    }

    private ProfissionalResponse toResponse(Profissional p) {
        return new ProfissionalResponse(
                p.getId(), p.getNome(), p.getCpf(), p.getConselho(),
                p.getEspecialidade(), p.getTelefone(), p.getEmail(),
                p.getCriadoEm(), p.getAtualizadoEm()
        );
    }
}
