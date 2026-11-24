package com.assistencia.api.json;

import java.util.*;

/** Converte mapas, listas, textos, números e booleanos em JSON (sem framework). */
public final class EscritorJson {
    private EscritorJson() { }

    public static String escrever(Object valor) {
        if (valor == null) {
            return "null";
        }
        if (valor instanceof Map) {
            return objeto((Map<?, ?>) valor);
        }
        if (valor instanceof Iterable) {
            return lista((Iterable<?>) valor);
        }
        if (valor instanceof Number || valor instanceof Boolean) {
            return String.valueOf(valor);
        }
        return TextoJson.entreAspas(valor);
    }

    private static String objeto(Map<?, ?> mapa) {
        StringJoiner membros = new StringJoiner(",", "{", "}");
        mapa.forEach((chave, valor) -> adicionar(membros, chave, valor));
        return membros.toString();
    }

    private static void adicionar(StringJoiner membros, Object chave, Object valor) {
        String nome = TextoJson.entreAspas(chave);
        membros.add(nome + ":" + escrever(valor));
    }

    private static String lista(Iterable<?> itens) {
        StringJoiner elementos = new StringJoiner(",", "[", "]");
        for (Object item : itens) {
            elementos.add(escrever(item));
        }
        return elementos.toString();
    }
}
