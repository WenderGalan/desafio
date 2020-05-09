package io.github.wendergalan.desafio;

import io.github.wendergalan.desafio.config.model.Validation;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Util.
 */
public class Util {

    /**
     * The constant DATE_TIME_FORMAT.
     */
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Criar lista de erros list.
     *
     * @param erros the erros
     * @return the list
     */
    public static List<Validation> criarListaDeErros(List<ObjectError> erros) {
        List<Validation> lista = new ArrayList<>();
        if (erros != null && !erros.isEmpty()) {
            for (ObjectError error : erros)
                lista.add(Validation.builder().field(((FieldError) error).getField()).message(error.getDefaultMessage()).build());
        }
        return lista;
    }
}
