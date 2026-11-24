package com.assistencia.dao.jdbc;

import java.util.List;
import static java.util.stream.Collectors.joining;

/** Guarda uma lista (ex.: adicionais) como texto separado por vírgulas; lista vazia vira nulo. */
final class TextoDeLista {
    private TextoDeLista() { }

    static String juntar(List<?> itens) {
        if (itens.isEmpty()) {
            return null;
        }
        return itens.stream()
                .map(String::valueOf)
                .collect(joining(","));
    }
}
