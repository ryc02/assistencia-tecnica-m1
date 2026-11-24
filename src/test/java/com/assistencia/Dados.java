package com.assistencia;

import com.assistencia.dados.Campos;

/** Monta um Campos a partir de pares chave/valor, para deixar os testes curtos. */
public final class Dados {
    private Dados() { }

    public static Campos de(Object... pares) {
        Campos campos = new Campos();
        for (int indice = 0; indice < pares.length; indice += 2) {
            campos.com((String) pares[indice], pares[indice + 1]);
        }
        return campos;
    }
}
