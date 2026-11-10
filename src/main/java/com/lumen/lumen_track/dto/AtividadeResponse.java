package com.lumen.lumen_track.dto;

import com.lumen.lumen_track.enums.Status;
import com.lumen.lumen_track.model.Atividade;

import java.time.LocalDate;

public class AtividadeResponse {

    private Long id;
    private String titulo;
    private String descricao;
    private Status status;
    private LocalDate dataCriacao;
    private LocalDate dataLimite;

    public AtividadeResponse(Atividade atividade) {
        this.id = atividade.getId();
        this.titulo = atividade.getTitulo();
        this.descricao = atividade.getDescricao();
        this.status = atividade.getStatus();
        this.dataCriacao = atividade.getDataCriacao();
        this.dataLimite = atividade.getDataLimite();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }
}