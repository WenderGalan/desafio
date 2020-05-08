package io.github.wendergalan.desafio.config.exception;

import io.github.wendergalan.desafio.config.models.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionConfiguration {

    @ExceptionHandler({Exception.class})
    public ResponseEntity handleErrorGeneric(Exception ex, WebRequest webRequest) {
        // Do Anything with webRequest
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(new ResponseError("Houve um problema no servidor. Por favor tente novamente mais tarde."));
    }
}