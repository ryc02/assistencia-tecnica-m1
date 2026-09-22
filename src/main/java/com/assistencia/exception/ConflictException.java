package com.assistencia.exception;

/**
 * [Requisito 10: Trata HTTP 409] Exceção lançada em conflitos de estado, CPF duplicado ou vínculos existentes.
 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
