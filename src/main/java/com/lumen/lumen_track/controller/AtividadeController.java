package com.lumen.lumen_track.controller;

import com.lumen.lumen_track.model.Atividade;
import com.lumen.lumen_track.service.AtividadeService;
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
    public Atividade cadastrar(@RequestBody Atividade atividade){
        return atividadeService.cadastrar(atividade);
    }

    @PutMapping("/{id}")
    public Atividade atualizar(@PathVariable Long id, @RequestBody Atividade novosDados){
        return atividadeService.atualizar(id, novosDados);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id){
        atividadeService.excluir(id);
    }

}
