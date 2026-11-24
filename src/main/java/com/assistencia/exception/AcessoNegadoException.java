package com.assistencia.exception;

/** Requisição bloqueada por segurança, como token CSRF inválido (HTTP 403). */
public class AcessoNegadoException extends RuntimeException {
    public AcessoNegadoException(String mensagem) {
        super(mensagem);
    }
}
