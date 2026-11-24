package com.assistencia.dados;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * [Calistenia - coleção de primeira classe] Conjunto de campos (nome → valor) que transporta dados entre as
 * camadas (Controller, Service e DAO).
 */
public final class Campos extends LeituraDeCampos {
    private final Map<String, Object> valores;

    public Campos() {
        valores = new LinkedHashMap<>();
    }

    public Campos(Map<String, ?> origem) {
        this();
        origem.forEach(this::com);
    }

    public Campos com(String chave, Object valor) {
        valores.put(chave, valor);
        return this;
    }

    public Campos comTodos(Campos outros) {
        outros.paraCada(this::com);
        return this;
    }

    public void paraCada(BiConsumer<String, Object> acao) {
        valores.forEach(acao);
    }

    @Override
    public Object valor(String chave) {
        return valores.get(chave);
    }

    public Map<String, Object> comoMapa() {
        return Collections.unmodifiableMap(valores);
    }
}
