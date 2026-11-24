package com.assistencia.api.json;

/** Lê valores JSON de forma recursiva (objetos, listas, textos e símbolos). */
final class AnalisadorJson {
    private final CursorJson cursor;

    AnalisadorJson(CursorJson cursor) {
        this.cursor = cursor;
    }

    Object valor() {
        cursor.pularEspacos();
        char atual = cursor.atual();
        if (atual == '{') {
            return ColecoesJson.objeto(cursor, this);
        }
        if (atual == '[') {
            return ColecoesJson.lista(cursor, this);
        }
        if (atual == '"') {
            return LeitorDeTextoJson.ler(cursor);
        }
        return LeitorDeSimboloJson.ler(cursor);
    }
}
