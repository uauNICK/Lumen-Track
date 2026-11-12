package com.lumen.lumen_track.controller;

import com.lumen.lumen_track.dto.AtividadeRequest;
import com.lumen.lumen_track.dto.AtividadeResponse;
import com.lumen.lumen_track.model.Atividade;
import com.lumen.lumen_track.service.AtividadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Listar todas as atividades")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de atividades retornada com sucesso"
            )
    })
    @GetMapping
    public List<AtividadeResponse> listarTodas() {
        return atividadeService.listarTodas();
    }
    @GetMapping("/{id}")
    public AtividadeResponse buscarPorId(@PathVariable Long id) {
        return atividadeService.buscarPorId(id);
    }


    @Operation(summary = "Cadastrar uma atividade")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Atividade cadastrada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados da atividade inválidos"
            )
    })
    @PostMapping
    public ResponseEntity<AtividadeResponse> cadastrar(@RequestBody AtividadeRequest request) {
        AtividadeResponse atividadeSalva = atividadeService.cadastrar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(atividadeSalva);
    }

    @Operation(summary = "Atualizar uma atividade")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Atividade atualizada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados da atividade inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Atividade não encontrada"
            )
    })

    @PutMapping("/{id}")
    public AtividadeResponse atualizar(@PathVariable Long id, @RequestBody AtividadeRequest request) {
        return atividadeService.atualizar(id, request);
    }

    @Operation(summary = "Excluir uma atividade")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Atividade excluída com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Atividade não encontrada"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        atividadeService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
