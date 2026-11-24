package com.assistencia.exception;

/** Dados inválidos informados pelo usuário (HTTP 400). */
public class ValidationException extends RuntimeException {
    public ValidationException(String mensagem) {
        super(mensagem);
    }
}
