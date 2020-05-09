package io.github.wendergalan.desafio.model.repository;

import io.github.wendergalan.desafio.model.entity.Licitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Licitacao repository.
 */
@Repository
public interface LicitacaoRepository extends JpaRepository<Licitacao, Long> {
}
