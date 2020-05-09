package io.github.wendergalan.desafio.service.impl;

import io.github.wendergalan.desafio.model.entity.Licitacao;
import io.github.wendergalan.desafio.model.entity.Proposta;
import io.github.wendergalan.desafio.model.repository.PropostaRepository;
import io.github.wendergalan.desafio.service.PropostaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static io.github.wendergalan.desafio.model.entity.Proposta.classificarPropostas;

/**
 * The type Proposta service.
 */
@Service
@RequiredArgsConstructor
public class PropostaServiceImpl implements PropostaService {

    private final PropostaRepository propostaRepository;

    @Override
    public Proposta salvar(Proposta proposta) {
        // Seta a data do cadastro
        if (proposta.getDataCadastro() == null)
            proposta.setDataCadastro(LocalDateTime.now(ZoneOffset.UTC));

        return propostaRepository.save(proposta);
    }

    @Override
    public void deletarPorId(Proposta proposta) {
        if (proposta == null || proposta.getId() == null)
            throw new IllegalArgumentException("A proposta não pode ser nula.");
        propostaRepository.delete(proposta);
    }

    @Override
    public Optional<Proposta> buscarPorId(Long id) {
        return propostaRepository.findById(id);
    }

    @Override
    public Page<Proposta> buscarTodasPropostas(Integer page, Integer size) {
        return propostaRepository.findAll(PageRequest.of(page - 1, size == -1 ? 10000000 : size));
    }

    @Override
    public Page<Proposta> buscarPropostasPorLicitacao(Licitacao licitacao, Pageable pageable) {
        return propostaRepository.findByLicitacaoOrderByClassificacaoAsc(licitacao, pageable);
    }

    @Override
    public void classificarPropostasPorLicitacao(Licitacao licitacao) {
        List<Proposta> propostas = propostaRepository.findAllByLicitacao(licitacao);
        classificarPropostas(licitacao.getTipoClassificacao(), propostas);
        propostaRepository.saveAll(propostas);
    }
}
