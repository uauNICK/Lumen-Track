package com.lumen.lumen_track.service;

import com.lumen.lumen_track.enums.Status;
import com.lumen.lumen_track.exception.AtividadeNaoEncontradaException;
import com.lumen.lumen_track.model.Atividade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AtividadeServiceTest {

    @Autowired
    private AtividadeService atividadeService;

    @Test
    void deveCadastrarAtividadeComSucesso() {

        // Arrange: preparar os dados
        Atividade atividade = new Atividade(
                "Estudar JUnit",
                "Aprender testes automatizados",
                LocalDate.of(2026, 11, 20)
        );

        // Act: executar a ação
        Atividade atividadeSalva = atividadeService.cadastrar(atividade);

        // Assert: verificar o resultado
        assertNotNull(atividadeSalva.getId());
        assertEquals("Estudar JUnit", atividadeSalva.getTitulo());
        assertEquals(Status.PENDENTE, atividadeSalva.getStatus());
    }

    @Test
    void deveBuscarAtividadePorIdComSucesso() {

        // Arrange
        Atividade atividade = new Atividade(
                "Estudar JUnit",
                "Aprender testes automatizados",
                LocalDate.of(2026, 11, 20)
        );

        Atividade atividadeSalva = atividadeService.cadastrar(atividade);

        // Act
        Atividade atividadeEncontrada =
                atividadeService.buscarPorId(atividadeSalva.getId());

        // Assert
        assertNotNull(atividadeEncontrada);
        assertEquals("Estudar JUnit", atividadeEncontrada.getTitulo());
    }

    @Test
    void deveLancarExcecaoQuandoAtividadeNaoExiste() {

        assertThrows(
                AtividadeNaoEncontradaException.class,
                () -> atividadeService.buscarPorId(999L)
        );

    }
    @Test
    void deveAtualizarAtividadeComSucesso() {

        // Arrange
        Atividade atividade = new Atividade(
                "Estudar JUnit",
                "Aprender testes automatizados",
                LocalDate.of(2026, 11, 20)
        );

        Atividade atividadeSalva = atividadeService.cadastrar(atividade);

        Atividade novosDados = new Atividade(
                "Estudar logica",
                "Aprender logica",
                LocalDate.of(2026, 11, 22)
        );

        // Act
        Atividade atividadeAtualizada = atividadeService.atualizar(
                atividadeSalva.getId(),
                novosDados
        );

        // Assert
        assertEquals("Estudar logica", atividadeAtualizada.getTitulo());
        assertEquals("Aprender logica", atividadeAtualizada.getDescricao());
        assertEquals(LocalDate.of(2026, 11, 22), atividadeAtualizada.getDataLimite());
        assertEquals(atividadeSalva.getId(), atividadeAtualizada.getId());
        assertEquals(atividadeSalva.getDataCriacao(), atividadeAtualizada.getDataCriacao());
    }

    @Test
    void deveExcluirAtividadeComSucesso() {

        // Arrange
        Atividade atividade = new Atividade(
                "Estudar JUnit",
                "Aprender testes automatizados",
                LocalDate.of(2026, 11, 20)
        );

        Atividade atividadeSalva = atividadeService.cadastrar(atividade);

        // Act
        atividadeService.excluir(atividadeSalva.getId());

        // Assert
        assertThrows(
                AtividadeNaoEncontradaException.class,
                () -> atividadeService.buscarPorId(atividadeSalva.getId())
        );
    }

}
