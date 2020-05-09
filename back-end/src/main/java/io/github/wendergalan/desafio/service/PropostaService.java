package io.github.wendergalan.desafio.service;

import io.github.wendergalan.desafio.model.entity.Licitacao;
import io.github.wendergalan.desafio.model.entity.Proposta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * The interface Proposta service.
 */
public interface PropostaService {

    /**
     * Salvar proposta.
     *
     * @param proposta the proposta
     * @return the proposta
     */
    public Proposta salvar(Proposta proposta);

    /**
     * Deletar por id.
     *
     * @param proposta the proposta
     */
    public void deletarPorId(Proposta proposta);

    /**
     * Buscar por id optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Proposta> buscarPorId(Long id);

    /**
     * Buscar todas propostas page.
     *
     * @param page the page
     * @param size the size
     * @return the page
     */
    public Page<Proposta> buscarTodasPropostas(Integer page, Integer size);

    /**
     * Buscar propostas por licitacao page.
     *
     * @param licitacao the licitacao
     * @param pageable  the pageable
     * @return the page
     */
    public Page<Proposta> buscarPropostasPorLicitacao(Licitacao licitacao, Pageable pageable);

    /**
     * Classificar propostas por licitacao.
     *
     * @param licitacao the licitacao
     */
    public void classificarPropostasPorLicitacao(Licitacao licitacao);
}
