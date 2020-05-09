package io.github.wendergalan.desafio.model.enums;

/**
 * The enum Tipo classificacao.
 */
public enum TipoClassificacao {
    /**
     * Menor preco tipo classificacao.
     */
    MENOR_PRECO(0),
    /**
     * Nota preco tipo classificacao.
     */
    NOTA_PRECO(1);

    private int value;

    TipoClassificacao(int value) {
        this.value = value;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public int getValue() {
        return value;
    }
}