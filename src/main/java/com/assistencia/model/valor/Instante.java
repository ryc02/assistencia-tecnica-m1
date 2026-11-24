package com.assistencia.model.valor;

import com.assistencia.dados.Campos;
import com.assistencia.exception.ValidationException;
import java.time.LocalDateTime;

/** Momento no tempo (data e hora); vazio quando não informado. */
final class Instante {
    private final LocalDateTime valor;

    Instante(LocalDateTime valor) {
        this.valor = valor;
    }

    boolean vazio() {
        return valor == null;
    }

    /** Exige que este instante (quando informado) não seja anterior à referência. */
    void exigirPosteriorA(Instante referencia, String mensagem) {
        if (valor != null) {
            referencia.exigirNaoDepoisDe(valor, mensagem);
        }
    }

    void gravarEm(Chave chave, Campos destino) {
        chave.gravar(destino, valor);
    }

    private void exigirNaoDepoisDe(LocalDateTime limite, String mensagem) {
        if (limite.isBefore(valor)) {
            throw new ValidationException(mensagem);
        }
    }
}
