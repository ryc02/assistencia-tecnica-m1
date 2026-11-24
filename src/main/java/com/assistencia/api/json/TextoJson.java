package com.assistencia.api.json;

import java.util.Map;

/**
 * Escreve um texto JSON entre aspas, escapando aspas, barras, quebras de linha e os
 * caracteres &lt; e &gt; (evita que o JSON seja interpretado como HTML - proteção contra XSS).
 */
final class TextoJson {
    private static final Map<Character, String> ESCAPES = Map.of('"', "\\\"", '\\', "\\\\", '\n', "\\n",
            '\r', "\\r", '\t', "\\t", '<', "\\u003c", '>', "\\u003e");

    private TextoJson() { }

    static String entreAspas(Object valor) {
        String texto = String.valueOf(valor);
        StringBuilder saida = new StringBuilder("\"");
        for (char caractere : texto.toCharArray()) {
            saida.append(escapar(caractere));
        }
        saida.append('"');
        return saida.toString();
    }

    private static String escapar(char caractere) {
        if (ESCAPES.containsKey(caractere)) {
            return ESCAPES.get(caractere);
        }
        if (caractere < 0x20) {
            return String.format("\\u%04x", (int) caractere);
        }
        return String.valueOf(caractere);
    }
}
