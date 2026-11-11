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

    public Atividade buscarPorId(Long id){
        return atividadeRepository.findById(id).orElseThrow(() ->
                new AtividadeNaoEncontradaException("Atividade não encontrada"));
    }

    public AtividadeResponse cadastrar(AtividadeRequest request) {
        Atividade atividade = request.toEntity();
        Atividade atividadeSalva = atividadeRepository.save(atividade);

        return new AtividadeResponse(atividadeSalva);
    }

    public Atividade atualizar(Long id, Atividade novosDados){
        Atividade atividade = atividadeRepository.findById(id)
                .orElseThrow(() -> new AtividadeNaoEncontradaException("Atividade não localizada"));

        atividade.atualizarTitulo(novosDados.getTitulo());
        atividade.atualizarDescricao(novosDados.getDescricao());
        atividade.alterarPrazo(novosDados.getDataLimite());

        return atividadeRepository.save(atividade);
        }

    public void excluir(Long id) {
        buscarPorId(id);
        atividadeRepository.deleteById(id);
    }


}
