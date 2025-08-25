package com.vidaplus.vida_plus_api.web;

import com.vidaplus.vida_plus_api.dto.PacienteDTOs.CreatePacienteRequest;
import com.vidaplus.vida_plus_api.dto.PacienteDTOs.PacienteResponse;
import com.vidaplus.vida_plus_api.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;


@RestController
@RequestMapping("/pacientes")
public class PacientesController {



    @GetMapping("/{id}")
public ResponseEntity<PacienteResponse> obter(@PathVariable Long id) {
    return ResponseEntity.ok(service.obter(id));
}

@PutMapping("/{id}")
public ResponseEntity<PacienteResponse> atualizar(@PathVariable Long id,
                      @RequestBody @Valid com.vidaplus.vida_plus_api.dto.PacienteDTOs.UpdatePacienteRequest req) {
    return ResponseEntity.ok(service.atualizar(id, req));
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> excluir(@PathVariable Long id) {
    service.excluir(id);
    return ResponseEntity.noContent().build();
}





    @GetMapping
public ResponseEntity<Page<PacienteResponse>> listar(
        @RequestParam(required = false) String nome,
        @PageableDefault(size = 10, sort = "nome") Pageable pageable
) {
    Page<PacienteResponse> page = service.listar(nome, pageable);
    return ResponseEntity.ok(page);
}




    private final PacienteService service;

    public PacientesController(PacienteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PacienteResponse> criar(@RequestBody @Valid CreatePacienteRequest req) {
        PacienteResponse resp = service.criar(req);
        return ResponseEntity.status(201).body(resp);
    }
}
