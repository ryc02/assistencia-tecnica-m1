package com.assistencia.api.json;

import java.util.*;

/** Lê objetos {...} e listas [...] JSON, delegando cada valor interno ao AnalisadorJson (recursão). */
final class ColecoesJson {
    private ColecoesJson() { }

    static Map<String, Object> objeto(CursorJson cursor, AnalisadorJson analisador) {
        Map<String, Object> mapa = new LinkedHashMap<>();
        cursor.exigir('{');
        if (cursor.consumir('}')) {
            return mapa;
        }
        do {
            String chave = LeitorDeTextoJson.ler(cursor);
            cursor.exigir(':');
            Object valor = analisador.valor();
            mapa.put(chave, valor);
        } while (cursor.consumir(','));
        cursor.exigir('}');
        return mapa;
    }

    static List<Object> lista(CursorJson cursor, AnalisadorJson analisador) {
        List<Object> itens = new ArrayList<>();
        cursor.exigir('[');
        if (cursor.consumir(']')) {
            return itens;
        }
        do {
            Object item = analisador.valor();
            itens.add(item);
        } while (cursor.consumir(','));
        cursor.exigir(']');
        return itens;
    }
}
