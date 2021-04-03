package com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception;

import com.desafioviavarejo.desafiospringmongo.app.core.exception.entity.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceUnavailableExceptionHandler {
    @ExceptionHandler({ServiceUnavailableException.class})
    public ResponseEntity<ApiError> handleBusinessException(ServiceUnavailableException e) {
        ApiError apiError = ApiError.builder().codigo(e.getCodigo()).mensagem(e.getMessage()).build();
        return new ResponseEntity<>(apiError, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
