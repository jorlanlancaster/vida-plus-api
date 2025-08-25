package com.vidaplus.vida_plus_api.web;

import com.vidaplus.vida_plus_api.dto.AgendamentoDTOs.*;
import com.vidaplus.vida_plus_api.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentosController {

    private final AgendamentoService service;

    public AgendamentosController(AgendamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponse> criar(@RequestBody @Valid CreateAgendamentoRequest req) {
        var resp = service.criar(req);
        return ResponseEntity.status(201).body(resp);
    }

    @GetMapping
    public ResponseEntity<Page<AgendamentoResponse>> listar(
            @RequestParam(required = false) Long profissionalId,
            @RequestParam(required = false) Long pacienteId,
            @RequestParam(required = false) java.time.LocalDate data,
            @PageableDefault(size = 10, sort = "inicio") Pageable pageable
    ) {
        var filtro = new FiltroQuery(profissionalId, pacienteId, data);
        return ResponseEntity.ok(service.listar(filtro, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        service.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
