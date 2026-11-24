package com.assistencia.model.valor;

import com.assistencia.dados.Campos;
import static java.lang.Boolean.TRUE;

/** Resposta sim/não de uma verificação; vazia quando não informada. */
final class Resposta {
    private final Boolean valor;

    Resposta(Boolean valor) {
        this.valor = valor;
    }

    boolean afirmativa() {
        return TRUE.equals(valor);
    }

    void gravarEm(Chave chave, Campos destino) {
        chave.gravar(destino, valor);
    }
}
