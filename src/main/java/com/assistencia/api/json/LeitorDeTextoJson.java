package com.assistencia.api.json;

import java.util.Map;

/** Lê um texto JSON ("...") tratando os escapes (\n, \", é etc.). */
final class LeitorDeTextoJson {
    private static final Map<Character, Character> ESCAPES = Map.of('n', '\n', 'r', '\r', 't', '\t',
            'b', '\b', 'f', '\f');

    private LeitorDeTextoJson() { }

    static String ler(CursorJson cursor) {
        cursor.exigir('"');
        StringBuilder texto = new StringBuilder();
        while (!cursor.fimDoTexto()) {
            texto.append(caractere(cursor));
        }
        cursor.exigir('"');
        return texto.toString();
    }

    private static char caractere(CursorJson cursor) {
        char lido = cursor.avancar();
        if (lido != '\\') {
            return lido;
        }
        char marcador = cursor.avancar();
        if (marcador == 'u') {
            return unicode(cursor);
        }
        return ESCAPES.getOrDefault(marcador, marcador);
    }

    private static char unicode(CursorJson cursor) {
        StringBuilder hexadecimal = new StringBuilder();
        for (int posicao = 0; posicao < 4; posicao++) {
            char lido = cursor.avancar();
            hexadecimal.append(lido);
        }
        String codigo = hexadecimal.toString();
        return (char) Integer.parseInt(codigo, 16);
    }
}
