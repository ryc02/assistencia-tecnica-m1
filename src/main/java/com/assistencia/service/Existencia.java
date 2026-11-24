package com.assistencia.service;

import com.assistencia.exception.*;

/** Regras comuns de existência: id obrigatório (HTTP 400) e registro encontrado (HTTP 404). */
public final class Existencia {
    private Existencia() { }

    public static Long exigirId(Long id, String entidade) {
        if (id == null) {
            throw new ValidationException("ID de " + entidade + " é obrigatório.");
        }
        return id;
    }

    public static <T> T exigir(T registro, String mensagem) {
        if (registro == null) {
            throw new NotFoundException(mensagem);
        }
        return registro;
    }
}
