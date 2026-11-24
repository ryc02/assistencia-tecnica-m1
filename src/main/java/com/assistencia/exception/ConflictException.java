package com.assistencia.exception;

/** Operação que viola uma regra de negócio ou de estado (HTTP 409). */
public class ConflictException extends RuntimeException {
    public ConflictException(String mensagem) {
        super(mensagem);
    }
}
