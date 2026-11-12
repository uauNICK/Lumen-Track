package com.lumen.lumen_track.service;

import com.lumen.lumen_track.dto.AtividadeRequest;
import com.lumen.lumen_track.dto.AtividadeResponse;
import com.lumen.lumen_track.exception.AtividadeNaoEncontradaException;
import com.lumen.lumen_track.model.Atividade;
import com.lumen.lumen_track.repository.AtividadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;

    public AtividadeService(AtividadeRepository atividadeRepository) {
        this.atividadeRepository = atividadeRepository;
    }

    //Métodos
    public List<AtividadeResponse> listarTodas() {

        return atividadeRepository.findAll()
                .stream()
                .map(AtividadeResponse::new)
                .toList();
    }

    public AtividadeResponse buscarPorId(Long id) {
        Atividade atividade = atividadeRepository.findById(id)
                .orElseThrow(() ->
                        new AtividadeNaoEncontradaException(
                                "Atividade não encontrada com ID: " + id
                        )
                );

        return new AtividadeResponse(atividade);
    }

    public AtividadeResponse cadastrar(AtividadeRequest request) {
        Atividade atividade = request.toEntity();
        Atividade atividadeSalva = atividadeRepository.save(atividade);

        return new AtividadeResponse(atividadeSalva);
    }

    public AtividadeResponse atualizar(Long id, AtividadeRequest request) {
        Atividade atividade = atividadeRepository.findById(id)
                .orElseThrow(() ->
                        new AtividadeNaoEncontradaException("Atividade não localizada")
                );

        atividade.atualizarTitulo(request.getTitulo());
        atividade.atualizarDescricao(request.getDescricao());
        atividade.alterarPrazo(request.getDataLimite());

        Atividade atividadeAtualizada = atividadeRepository.save(atividade);

        return new AtividadeResponse(atividadeAtualizada);
    }

    public void excluir(Long id) {
        buscarPorId(id);
        atividadeRepository.deleteById(id);
    }


}
