package com.assistencia.exception;

/**
 * [Requisito 10: Trata HTTP 404] Exceção lançada quando o registro consultado não existe.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
