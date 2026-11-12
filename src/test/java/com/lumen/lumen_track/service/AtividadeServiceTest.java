package com.lumen.lumen_track.service;

import com.lumen.lumen_track.dto.AtividadeRequest;
import com.lumen.lumen_track.dto.AtividadeResponse;
import com.lumen.lumen_track.enums.Status;
import com.lumen.lumen_track.exception.AtividadeNaoEncontradaException;
import com.lumen.lumen_track.model.Atividade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class AtividadeServiceTest {

    @Autowired
    private AtividadeService atividadeService;

    @Test
    void deveCadastrarAtividadeComSucesso() {

        // Arrange
        AtividadeRequest request = new AtividadeRequest(
                "Estudar JUnit",
                "Aprender testes automatizados",
                LocalDate.of(2026, 11, 20)
        );

        // Act
        AtividadeResponse atividadeSalva = atividadeService.cadastrar(request);

        // Assert
        assertNotNull(atividadeSalva.getId());
        assertEquals("Estudar JUnit", atividadeSalva.getTitulo());
        assertEquals(Status.PENDENTE, atividadeSalva.getStatus());
    }

    @Test
    void deveBuscarAtividadePorIdComSucesso() {

        // Arrange
        AtividadeRequest request = new AtividadeRequest(
                "Estudar JUnit",
                "Aprender testes automatizados",
                LocalDate.of(2026, 11, 20)
        );

        AtividadeResponse atividadeSalva = atividadeService.cadastrar(request);

        // Act
        AtividadeResponse atividadeEncontrada =
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
        AtividadeRequest request = new AtividadeRequest(
                "Estudar Spring",
                "Estudar orientação a objetos",
                LocalDate.of(2026, 11, 20)
        );

        AtividadeResponse atividadeSalva = atividadeService.cadastrar(request);

        AtividadeRequest novosDados = new AtividadeRequest(
                "Estudar Spring",
                "Aprender Spring Boot",
                LocalDate.of(2026, 11, 22)
        );

        // Act
        AtividadeResponse atividadeAtualizada =
                atividadeService.atualizar(atividadeSalva.getId(), novosDados);

        // Assert
        assertEquals("Estudar Spring", atividadeAtualizada.getTitulo());
        assertEquals("Aprender Spring Boot", atividadeAtualizada.getDescricao());
        assertEquals(LocalDate.of(2026, 11, 22), atividadeAtualizada.getDataLimite());
    }

    @Test
    void deveExcluirAtividadeComSucesso() {

        // Arrange
        AtividadeRequest request = new AtividadeRequest(
                "Estudar Java",
                "Praticar operações CRUD",
                LocalDate.of(2026, 11, 20)
        );

        AtividadeResponse atividadeSalva = atividadeService.cadastrar(request);

        // Act
        atividadeService.excluir(atividadeSalva.getId());

        // Assert
        assertThrows(
                AtividadeNaoEncontradaException.class,
                () -> atividadeService.buscarPorId(atividadeSalva.getId())
        );
    }

}
