package com.assistencia.api.json;

import java.math.BigDecimal;

/** Lê os valores JSON que não são texto, objeto ou lista: true, false, null e números. */
final class LeitorDeSimboloJson {
    private static final String CARACTERES_DE_SIMBOLO = "+-.0123456789eEtrufalsn";

    private LeitorDeSimboloJson() { }

    static Object ler(CursorJson cursor) {
        StringBuilder simbolo = new StringBuilder();
        while (ehSimbolo(cursor.atual())) {
            char lido = cursor.avancar();
            simbolo.append(lido);
        }
        return interpretar(simbolo.toString(), cursor);
    }

    private static boolean ehSimbolo(char caractere) {
        return CARACTERES_DE_SIMBOLO.indexOf(caractere) >= 0;
    }

    private static Object interpretar(String simbolo, CursorJson cursor) {
        switch (simbolo) {
            case "true":
                return Boolean.TRUE;
            case "false":
                return Boolean.FALSE;
            case "null":
                return null;
            default:
                return numero(simbolo, cursor);
        }
    }

    private static BigDecimal numero(String simbolo, CursorJson cursor) {
        try {
            return new BigDecimal(simbolo);
        } catch (NumberFormatException excecao) {
            throw cursor.erro();
        }
    }
}
