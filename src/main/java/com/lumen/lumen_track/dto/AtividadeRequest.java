package com.lumen.lumen_track.dto;

import com.lumen.lumen_track.model.Atividade;

import java.time.LocalDate;

public class AtividadeRequest {
    private String titulo;
    private String descricao;
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
