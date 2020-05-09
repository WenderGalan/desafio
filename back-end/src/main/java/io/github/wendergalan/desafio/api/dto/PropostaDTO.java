package io.github.wendergalan.desafio.api.dto;

import io.github.wendergalan.desafio.model.entity.Licitacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * The type Proposta dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropostaDTO {

    private Long id;

    @NotNull
    private String fornecedor;

    private Double nota;

    @NotNull
    private Double preco;

    private LocalDateTime dataCadastro;

    private Integer classificacao;

    private LicitacaoDTO licitacao;
}
