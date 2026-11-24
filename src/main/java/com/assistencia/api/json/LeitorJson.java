package com.assistencia.api.json;

import com.assistencia.exception.ValidationException;
import java.util.Map;

/** Lê o corpo JSON de uma requisição REST, que deve ser um objeto ({...}). Corpo vazio vira objeto vazio. */
public final class LeitorJson {
    private LeitorJson() { }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> lerObjeto(String json) {
        if (json == null || json.isBlank()) {
            return Map.of();
        }
        CursorJson cursor = new CursorJson(json);
        Object valor = new AnalisadorJson(cursor).valor();
        cursor.pularEspacos();
        if (!(valor instanceof Map) || !cursor.terminou()) {
            throw new ValidationException("Corpo da requisição deve ser um objeto JSON válido.");
        }
        return (Map<String, Object>) valor;
    }
}
