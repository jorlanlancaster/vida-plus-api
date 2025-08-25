package com.vidaplus.vida_plus_api.web;

import com.vidaplus.vida_plus_api.domain.Auditoria;
import com.vidaplus.vida_plus_api.repository.AuditoriaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auditoria")
public class AuditoriaController {

    private final AuditoriaRepository repo;

    public AuditoriaController(AuditoriaRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<Page<Auditoria>> listar(@PageableDefault(size = 10, sort = "dataHora") Pageable pageable) {
        return ResponseEntity.ok(repo.findAll(pageable));
    }
}
