package io.github.wendergalan.desafio.service;

import io.github.wendergalan.desafio.model.entity.Licitacao;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * The interface Licitacao service.
 */
public interface LicitacaoService {

    /**
     * Alterar licitacao.
     *
     * @param licitacao the licitacao
     * @return the licitacao
     */
    public Licitacao alterar(Licitacao licitacao);

    /**
     * Salvar licitacao.
     *
     * @param licitacao the licitacao
     * @return the licitacao
     */
    public Licitacao salvar(Licitacao licitacao);

    /**
     * Deletar por id.
     *
     * @param licitacao the licitacao
     */
    public void deletarPorId(Licitacao licitacao);

    /**
     * Buscar por id optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Licitacao> buscarPorId(Long id);

    /**
     * Buscar todas licitacaos page.
     *
     * @param page the page
     * @param size the size
     * @return the page
     */
    public Page<Licitacao> buscarTodasLicitacaos(Integer page, Integer size);
}
