package io.github.wendergalan.desafio.config.exception;

import io.github.wendergalan.desafio.config.model.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

/**
 * The type Global exception configuration.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionConfiguration {

    /**
     * Handle error generic response entity.
     *
     * @param ex         the ex
     * @param webRequest the web request
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleErrorGeneric(Exception ex, WebRequest webRequest) {
        // Do Anything with webRequest
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(new ResponseError("Houve um problema no servidor. Por favor tente novamente mais tarde."));
    }

    /**
     * Handle response status exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity handleResponseStatusException(ResponseStatusException ex) {
        return new ResponseEntity(ex.getStatus());
    }

    /**
     * Handle validation exceptions response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}