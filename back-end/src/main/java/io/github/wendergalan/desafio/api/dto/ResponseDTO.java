package io.github.wendergalan.desafio.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * The type Response dto.
 *
 * @param <T> the type parameter
 */
@AllArgsConstructor
@Getter
@Setter
public class ResponseDTO<T> implements Serializable {
    private Integer currentPage;
    private Integer totalPages;
    private Long totalElements;
    private List<T> content;
}
