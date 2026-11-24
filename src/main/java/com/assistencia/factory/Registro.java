package com.assistencia.factory;

import com.assistencia.exception.OperacaoNaoSuportadaException;
import java.util.*;

/** [Calistenia - coleção de primeira classe] Objetos registrados pelo nome (ex.: recursos REST). */
public final class Registro<T> {
    private final Map<String, T> itens = new HashMap<>();

    void registrar(String nome, T item) {
        itens.put(nome, item);
    }

    public T buscar(String nome) {
        return itens.get(nome);
    }

    /** Item registrado com o nome; se não houver, o recurso não suporta a operação (405). */
    public T exigir(String nome) {
        if (!possui(nome)) {
            throw new OperacaoNaoSuportadaException("Operação não suportada para este recurso.");
        }
        return itens.get(nome);
    }

    public boolean possui(String nome) {
        return itens.containsKey(nome);
    }
}
