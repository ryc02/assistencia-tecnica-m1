package com.assistencia.dao.jdbc;

import com.assistencia.dados.*;
import java.util.*;

/** [Calistenia - coleção de primeira classe] Linhas devolvidas por uma consulta SQL. */
public final class Linhas {
    private final List<Campos> itens;

    Linhas(List<Campos> itens) {
        this.itens = itens;
    }

    public <T extends Entidade> Registros<T> converter(ConversorDeLinha<T> conversor) throws Exception {
        List<T> entidades = new ArrayList<>();
        for (Campos linha : itens) {
            T entidade = conversor.converter(linha);
            entidades.add(entidade);
        }
        return new Registros<>(entidades);
    }

    public <T> T primeira(ConversorDeLinha<T> conversor) throws Exception {
        if (itens.isEmpty()) {
            return null;
        }
        Campos primeira = itens.get(0);
        return conversor.converter(primeira);
    }

    public Long total() throws Exception {
        return primeira(linha -> linha.inteiroLongo("total"));
    }
}
