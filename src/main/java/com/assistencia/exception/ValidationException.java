package com.assistencia.exception;

/**
 * [Requisito 10: Trata HTTP 400] Exceção lançada quando os dados de entrada violam as regras do sistema.
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
