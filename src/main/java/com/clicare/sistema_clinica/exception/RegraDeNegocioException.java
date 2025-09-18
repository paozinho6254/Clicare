package com.clicare.sistema_clinica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Quando esta exceção é lançada de um Controller, ela retorna um status HTTP 400 (Bad Request)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegraDeNegocioException extends RuntimeException {

    public RegraDeNegocioException(String message) {
        super(message);
    }
}