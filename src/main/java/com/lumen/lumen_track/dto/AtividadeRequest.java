package com.lumen.lumen_track.dto;

import com.lumen.lumen_track.model.Atividade;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class AtividadeRequest {

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotNull(message = "A data limite é obrigatória")
    @FutureOrPresent(message = "A data limite não pode estar no passado")
    private LocalDate dataLimite;

    public AtividadeRequest(String titulo, String descricao, LocalDate dataLimite) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataLimite = dataLimite;
    }



    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public Atividade toEntity() {
        return new Atividade(
                titulo,
                descricao,
                dataLimite
        );
    }


}
