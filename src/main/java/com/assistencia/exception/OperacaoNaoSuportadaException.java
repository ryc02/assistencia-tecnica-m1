package com.assistencia.exception;

/** Método HTTP não suportado pelo recurso ou pela ação (HTTP 405). */
public class OperacaoNaoSuportadaException extends RuntimeException {
    public OperacaoNaoSuportadaException(String mensagem) {
        super(mensagem);
    }
}
