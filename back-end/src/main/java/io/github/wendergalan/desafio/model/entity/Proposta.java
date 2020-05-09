package io.github.wendergalan.desafio.model.entity;

import io.github.wendergalan.desafio.model.enums.TipoClassificacao;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Proposta.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "proposta", schema = "public")
public class Proposta {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "fornecedor", nullable = false)
    @NotNull
    private String fornecedor;

    private Long nota;

    @Column(name = "preco", nullable = false)
    @NotNull
    private Long preco;

    @Column(name = "data_cadastro", nullable = false)
    @NotNull
    private LocalDateTime dataCadastro;

    private Integer classificacao;

    @ManyToOne(fetch = FetchType.LAZY)
    private Licitacao licitacao;

    /**
     * Classificar propostas.
     *
     * @param tipoClassificacao the tipo classificacao
     * @param propostas         the propostas
     */
    public static void classificarPropostas(TipoClassificacao tipoClassificacao, List<Proposta> propostas) {
        if (propostas != null && !propostas.isEmpty()) {
            switch (tipoClassificacao) {
                case NOTA_PRECO:
                    classificarPorNotaPreco(propostas);
                    break;
                case MENOR_PRECO:
                    classificarPorMenorPreco(propostas);
                    break;
            }
            for (int i = 0; i < propostas.size(); i++)
                propostas.get(i).setClassificacao(i + 1);
        }
    }

    /**
     * Classificar por nota preco.
     *
     * @param propostas the propostas
     */
    public static void classificarPorNotaPreco(List<Proposta> propostas) {
        propostas.sort((p1, p2) -> {
            int nota = p1.getNota().compareTo(p2.getNota());
            if (nota == 0) {
                int preco = p1.getPreco().compareTo(p2.getPreco());
                if (preco == 0) {
                    return p1.getDataCadastro().compareTo(p2.getDataCadastro());
                }
            }
            return nota;
        });
    }

    /**
     * Classificar por menor preco.
     *
     * @param propostas the propostas
     */
    public static void classificarPorMenorPreco(List<Proposta> propostas) {
        propostas.sort((p1, p2) -> {
            int preco = p1.getPreco().compareTo(p2.getPreco());
            if (preco == 0)
                return p1.getDataCadastro().compareTo(p2.getDataCadastro());
            return preco;
        });
    }
}
