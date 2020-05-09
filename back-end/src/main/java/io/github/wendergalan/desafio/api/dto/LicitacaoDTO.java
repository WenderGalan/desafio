package io.github.wendergalan.desafio.api.dto;


import io.github.wendergalan.desafio.model.enums.TipoClassificacao;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * The type Licitacao dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LicitacaoDTO {

    private Long id;

    @NotNull
    private String descricao;

    @NonNull
    private TipoClassificacao tipoClassificacao;
}
