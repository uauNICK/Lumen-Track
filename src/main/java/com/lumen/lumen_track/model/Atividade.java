package com.lumen.lumen_track.model;

import com.lumen.lumen_track.enums.Status;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "Representa uma atividade cadastrada no sistema.")
@Entity
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único da atividade, gerado automaticamente",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status atual da atividade", example = "PENDENTE")
    private Status status;

    @Schema(
            description = "Título da atividade",
            example = "Organizar estoque"
    )
    private String titulo;

    @Schema(
            description = "Descrição detalhada da atividade",
            example = "Conferir e organizar os materiais do estoque"
    )
    private String descricao;

    @Schema(
            description = "Data em que a atividade foi criada, preenchida automaticamente pelo sistema",
            example = "2026-11-10",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDate dataCriacao;

    @Schema(
            description = "Data limite para conclusão da atividade",
            example = "2026-11-20"
    )
    private LocalDate dataLimite;

    public Atividade() {
    }

    public Atividade(String titulo, String descricao, LocalDate dataLimite) {

        //Validação de dados

        if(titulo == null || titulo.isBlank()){
            throw new IllegalArgumentException("Titulo incorreto");
        }

        if(descricao == null || descricao.isBlank()){
            throw new IllegalArgumentException("Descricao incorreto");
        }

        if(dataLimite == null){
            throw new IllegalArgumentException("Data limite inválida");
        }

        if(dataLimite.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Data Limite inválida, não pode ser no passado");
        }


        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = LocalDate.now();
        this.status = Status.PENDENTE;
        this.dataLimite = dataLimite;



    }
    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }
    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    //Métodos

    public void atualizarTitulo(String novoTitulo) {

        if(novoTitulo == null || novoTitulo.isBlank()){
            throw new IllegalArgumentException("Novo titulo inválido");
        }


        this.titulo = novoTitulo;
    }

    public void atualizarDescricao(String novaDescricao) {
        if(novaDescricao == null || novaDescricao.isBlank()){
            throw new IllegalArgumentException("Nova descrição inválida");
        }

        this.descricao = novaDescricao;
    }

    public void alterarPrazo(LocalDate novoPrazo) {
        if(novoPrazo == null){
            throw new IllegalArgumentException("Novo prazo inválido");
        }
        if(novoPrazo.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Data do novo prazo inválida");
        }
        this.dataLimite = novoPrazo;

    }

    public void alterarStatus(Status novoStatus) {
        if(novoStatus == null){
            throw new IllegalArgumentException("Novo status inválido");
        }
        this.status = novoStatus;
    }

}
