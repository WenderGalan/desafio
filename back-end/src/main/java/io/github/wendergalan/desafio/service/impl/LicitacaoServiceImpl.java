package io.github.wendergalan.desafio.service.impl;

import io.github.wendergalan.desafio.model.entity.Licitacao;
import io.github.wendergalan.desafio.model.repository.LicitacaoRepository;
import io.github.wendergalan.desafio.service.LicitacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The type Licitacao service.
 */
@Service
@RequiredArgsConstructor
public class LicitacaoServiceImpl implements LicitacaoService {

    private final LicitacaoRepository licitacaoRepository;

    @Override
    public Licitacao alterar(Licitacao licitacao) {
        return licitacaoRepository.save(licitacao);
    }

    @Override
    public Licitacao salvar(Licitacao licitacao) {
        return licitacaoRepository.save(licitacao);
    }

    @Override
    public void deletarPorId(Licitacao licitacao) {
        if (licitacao == null || licitacao.getId() == null)
            throw new IllegalArgumentException("A licitacao não pode ser nula.");
        licitacaoRepository.delete(licitacao);
    }

    @Override
    public Optional<Licitacao> buscarPorId(Long id) {
        return licitacaoRepository.findById(id);
    }

    @Override
    public Page<Licitacao> buscarTodasLicitacaos(Integer page, Integer size) {
        return licitacaoRepository.findAll(PageRequest.of(page - 1, size == -1 ? 10000000 : size));
    }
}
