package io.github.wendergalan.desafio.model.repository;

import io.github.wendergalan.desafio.model.entity.Licitacao;
import io.github.wendergalan.desafio.model.entity.Proposta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Proposta repository.
 */
@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    /**
     * Find by licitacao order by classificacao asc page.
     *
     * @param licitacao the licitacao
     * @param pageable  the pageable
     * @return the page
     */
    Page<Proposta> findByLicitacaoOrderByClassificacaoAsc(Licitacao licitacao, Pageable pageable);

    /**
     * Find all by licitacao list.
     *
     * @param licitacao the licitacao
     * @return the list
     */
    List<Proposta> findAllByLicitacao(Licitacao licitacao);
}
