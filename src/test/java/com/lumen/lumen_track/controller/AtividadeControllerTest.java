
package com.lumen.lumen_track.controller;

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
        when(atividadeService.listarTodas())
                .thenReturn(List.of());

        // Act
        mockMvc.perform(get("/atividades")
                        .contentType(MediaType.APPLICATION_JSON))

                // Assert
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void deveBuscarAtividadePorId() throws Exception {

        // Arrange
        Atividade atividade = new Atividade(
                "Estudar Java",
                "Praticar testes com Spring Boot",
                LocalDate.of(2026, 11, 15)
        );

        when(atividadeService.buscarPorId(1L))
                .thenReturn(atividade);

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
        Atividade atividade = new Atividade(
                "Estudar Spring Boot",
                "Aprender testes de API",
                LocalDate.of(2026, 11, 20)
        );

        when(atividadeService.cadastrar(any(Atividade.class)))
                .thenReturn(atividade);

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
                .andExpect(status().isOk())
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
                "Estudar Java e Spring Boot",
                "Praticar testes de API REST",
                LocalDate.of(2026, 11, 25)
        );

        when(atividadeService.atualizar(
                eq(1L),
                any(Atividade.class)
        )).thenReturn(atividadeAtualizada);

        // Act
        mockMvc.perform(put("/atividades/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "titulo": "Estudar Java e Spring Boot",
                                "descricao": "Praticar testes de API REST",
                                "dataLimite": "2026-11-25"
                            }
                            """))

                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo")
                        .value("Estudar Java e Spring Boot"))
                .andExpect(jsonPath("$.descricao")
                        .value("Praticar testes de API REST"));
    }


    @Test
    void deveExcluirAtividade() throws Exception {

        // Act
        mockMvc.perform(delete("/atividades/1"))

                // Assert
                .andExpect(status().isOk());

        // Verifica se o Service foi chamado com o ID correto
        verify(atividadeService).excluir(1L);
    }

}
