package io.github.wendergalan.desafio;

import io.github.wendergalan.desafio.model.entity.Licitacao;
import io.github.wendergalan.desafio.model.entity.Proposta;
import io.github.wendergalan.desafio.service.LicitacaoService;
import io.github.wendergalan.desafio.service.PropostaService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static io.github.wendergalan.desafio.model.enums.TipoClassificacao.MENOR_PRECO;
import static io.github.wendergalan.desafio.model.enums.TipoClassificacao.NOTA_PRECO;

/**
 * The type Desafio application.
 */
@RequiredArgsConstructor
@SpringBootApplication
public class DesafioApplication extends SpringBootServletInitializer {

    private final LicitacaoService licitacaoService;
    private final PropostaService propostaService;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DesafioApplication.class);
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(DesafioApplication.class, args);
    }

    /**
     * Faz a inserção dos itens que estavam descritos no README do projeto.
     */
    @PostConstruct
    @Transactional
    public void onLoad() {
        Licitacao l1 = Licitacao.builder().descricao("COMPRA DE ÔNIBUS").tipoClassificacao(NOTA_PRECO).build();
        Licitacao l2 = Licitacao.builder().descricao("COMPRA DE COMPUTADORES").tipoClassificacao(MENOR_PRECO).build();
        licitacaoService.salvar(l1);
        licitacaoService.salvar(l2);

        Proposta p1 = Proposta.builder().licitacao(l1).fornecedor("Fornecedor A").nota(8D).preco(110D).dataCadastro(LocalDateTime.of(2019, 10, 23, 10, 0)).build();
        Proposta p2 = Proposta.builder().licitacao(l1).fornecedor("Fornecedor B").nota(8D).preco(110D).dataCadastro(LocalDateTime.of(2019, 10, 23, 10, 30)).build();
        Proposta p3 = Proposta.builder().licitacao(l1).fornecedor("Fornecedor C").nota(8.9).preco(115D).dataCadastro(LocalDateTime.of(2019, 10, 23, 10, 40)).build();
        Proposta p4 = Proposta.builder().licitacao(l1).fornecedor("Fornecedor D").nota(7.5).preco(90D).dataCadastro(LocalDateTime.of(2019, 10, 23, 10, 50)).build();

        Proposta p5 = Proposta.builder().licitacao(l2).fornecedor("Fornecedor A").preco(115D).dataCadastro(LocalDateTime.of(2019, 10, 23, 10, 0)).build();
        Proposta p6 = Proposta.builder().licitacao(l2).fornecedor("Fornecedor B").preco(115D).dataCadastro(LocalDateTime.of(2019, 10, 23, 10, 30)).build();
        Proposta p7 = Proposta.builder().licitacao(l2).fornecedor("Fornecedor C").preco(100D).dataCadastro(LocalDateTime.of(2019, 10, 23, 10, 40)).build();
        propostaService.salvar(p1);
        propostaService.salvar(p2);
        propostaService.salvar(p3);
        propostaService.salvar(p4);
        propostaService.salvar(p5);
        propostaService.salvar(p6);
        propostaService.salvar(p7);
    }
}
