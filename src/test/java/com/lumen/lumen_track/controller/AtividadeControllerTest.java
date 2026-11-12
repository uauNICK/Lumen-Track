
package com.lumen.lumen_track.controller;

import com.lumen.lumen_track.dto.AtividadeRequest;
import com.lumen.lumen_track.dto.AtividadeResponse;
import com.lumen.lumen_track.exception.AtividadeNaoEncontradaException;
import com.lumen.lumen_track.model.Atividade;
import com.lumen.lumen_track.service.AtividadeService;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AtividadeController.class)
@AutoConfigureMockMvc
class AtividadeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AtividadeService atividadeService;

    @Test
    void deveRetornarListaDeAtividades() throws Exception {

        // Arrange
        Atividade atividade = new Atividade(
                "Estudar Java",
                "Praticar DTOs",
                LocalDate.of(2026, 11, 20)
        );

        AtividadeResponse response = new AtividadeResponse(atividade);

        when(atividadeService.listarTodas())
                .thenReturn(List.of(response));

        // Act
        mockMvc.perform(get("/atividades")
                        .contentType(MediaType.APPLICATION_JSON))

                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Estudar Java"))
                .andExpect(jsonPath("$[0].descricao").value("Praticar DTOs"))
                .andExpect(jsonPath("$[0].status").value("PENDENTE"));
    }

    @Test
    void deveBuscarAtividadePorId() throws Exception {

        // Arrange
        Atividade atividade = new Atividade(
                "Estudar Java",
                "Praticar testes com Spring Boot",
                LocalDate.of(2026, 11, 15)
        );

        AtividadeResponse response = new AtividadeResponse(atividade);

        when(atividadeService.buscarPorId(1L)).thenReturn(response);

        // Act
        mockMvc.perform(get("/atividades/1")
                        .contentType(MediaType.APPLICATION_JSON))

                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Estudar Java"))
                .andExpect(jsonPath("$.descricao")
                        .value("Praticar testes com Spring Boot"));
    }

    @Test
    void deveCadastrarAtividade() throws Exception {

        // Arrange
        AtividadeRequest request = new AtividadeRequest(
                "Estudar Spring Boot",
                "Aprender testes de API",
                LocalDate.of(2026, 11, 20)
        );

        AtividadeResponse response = new AtividadeResponse(
                new Atividade(
                        "Estudar Spring Boot",
                        "Aprender testes de API",
                        LocalDate.of(2026, 11, 20)
                )
        );

        when(atividadeService.cadastrar(any(AtividadeRequest.class)))
                .thenReturn(response);

        // Act
        mockMvc.perform(post("/atividades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "titulo": "Estudar Spring Boot",
                                "descricao": "Aprender testes de API",
                                "dataLimite": "2026-11-20"
                            }
                            """))

                // Assert
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo")
                        .value("Estudar Spring Boot"))
                .andExpect(jsonPath("$.descricao")
                        .value("Aprender testes de API"))
                .andExpect(jsonPath("$.status")
                        .value("PENDENTE"));
    }

    @Test
    void deveAtualizarAtividade() throws Exception {

        // Arrange
        Atividade atividadeAtualizada = new Atividade(
                "Estudar Spring",
                "Praticar testes de API REST",
                LocalDate.of(2026, 11, 25)
        );

        when(atividadeService.atualizar(
                eq(1L),
                any(AtividadeRequest.class)
        )).thenReturn(new AtividadeResponse(atividadeAtualizada));

        // Act
        mockMvc.perform(put("/atividades/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "titulo": "Estudar Spring",
                            "descricao": "Praticar testes de API REST",
                            "dataLimite": "2026-11-25"
                        }
                        """))

                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo")
                        .value("Estudar Spring"))
                .andExpect(jsonPath("$.descricao")
                        .value("Praticar testes de API REST"));
    }

    @Test
    void deveExcluirAtividade() throws Exception {

        // Act
        mockMvc.perform(delete("/atividades/1"))

                // Assert
                .andExpect(status().isNoContent());

        // Verifica se o Service foi chamado com o ID correto
        verify(atividadeService).excluir(1L);
    }


    @Test
    void deveRetornar404QuandoAtividadeNaoExistir() throws Exception {

        // Arrange
        when(atividadeService.buscarPorId(999L))
                .thenThrow(new AtividadeNaoEncontradaException(
                        "Atividade não encontrada"
                ));

        // Act
        mockMvc.perform(get("/atividades/999"))

                // Assert
                .andExpect(status().isNotFound())
                .andExpect(content().string(
                        "Atividade não encontrada"
                ));
    }


    @Test
    void deveRetornar400QuandoDadosForemInvalidos() throws Exception {

        // Arrange
        when(atividadeService.cadastrar(any(AtividadeRequest.class)))
                .thenThrow(new IllegalArgumentException(
                        "O título não pode ser vazio"
                ));

        // Act
        mockMvc.perform(post("/atividades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "titulo": "",
                                "descricao": "Descrição válida",
                                "dataLimite": "2026-11-20"
                            }
                            """))

                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        "O título não pode ser vazio"
                ));
    }

    @Test
    void deveRetornar400QuandoServiceRejeitarDados() throws Exception {
        when(atividadeService.cadastrar(any(AtividadeRequest.class)))
                .thenThrow(new IllegalArgumentException(
                        "O título não pode ser vazio"
                ));

        mockMvc.perform(post("/atividades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "titulo": "Título válido",
                                "descricao": "Descrição válida",
                                "dataLimite": "2026-11-20"
                            }
                            """))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("O título não pode ser vazio"));
    }


}
