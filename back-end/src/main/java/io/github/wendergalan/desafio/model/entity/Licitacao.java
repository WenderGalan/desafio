package io.github.wendergalan.desafio.model.entity;

import io.github.wendergalan.desafio.model.enums.TipoClassificacao;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.CascadeType.REMOVE;

/**
 * The type Licitacao.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "licitacao", schema = "public")
public class Licitacao implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "descricao", nullable = false)
    @NotNull
    private String descricao;

    @Column(name = "tipo_classificacao", nullable = false)
    @NonNull
    private TipoClassificacao tipoClassificacao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "licitacao", cascade = REMOVE)
    private List<Proposta> propostas;
}
