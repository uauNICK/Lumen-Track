package com.lumen.lumen_track.controller;

import com.lumen.lumen_track.model.Atividade;
import com.lumen.lumen_track.service.AtividadeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {
    private final AtividadeService atividadeService;

    public AtividadeController(AtividadeService atividadeService) {
        this.atividadeService = atividadeService;
    }

    @GetMapping
    public List<Atividade> findAll() {
        return atividadeService.listarTodas();
    }
    @GetMapping("/{id}")
    public Atividade buscarPorId(@PathVariable Long id){
        return atividadeService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Atividade> cadastrar(@RequestBody Atividade atividade) {
        Atividade atividadeSalva = atividadeService.cadastrar(atividade);

        return ResponseEntity.status(HttpStatus.CREATED).body(atividadeSalva);
    }
    @PutMapping("/{id}")
    public Atividade atualizar(@PathVariable Long id, @RequestBody Atividade novosDados){
        return atividadeService.atualizar(id, novosDados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        atividadeService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
