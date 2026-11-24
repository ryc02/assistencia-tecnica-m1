package com.assistencia.model.valor;

import com.assistencia.exception.ValidationException;

/** Regra de tamanho máximo para textos informados pelo usuário. */
final class LimiteDeTamanho {
    private LimiteDeTamanho() { }

    static void exigir(String valor, int limite, String rotulo) {
        if (valor != null && valor.length() > limite) {
            throw new ValidationException(rotulo + " não pode exceder " + limite + " caracteres.");
        }
    }
}
