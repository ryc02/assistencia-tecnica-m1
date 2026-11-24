package com.assistencia.dados;

import java.util.*;
import java.util.function.Predicate;
import static java.util.stream.Collectors.toList;

/** [Calistenia - coleção de primeira classe] Lista de entidades devolvida pelas consultas. */
public final class Registros<T extends Entidade> {
    private final List<T> itens;

    public Registros(List<T> origem) {
        itens = List.copyOf(origem);
    }

    public static <T extends Entidade> Registros<T> unicoOuVazio(T item) {
        if (item == null) {
            return new Registros<>(List.of());
        }
        return new Registros<>(List.of(item));
    }

    public boolean vazio() {
        return itens.isEmpty();
    }

    public int quantidade() {
        return itens.size();
    }

    public long contar(Predicate<T> criterio) {
        return itens.stream()
                .filter(criterio)
                .count();
    }

    public List<Map<String, Object>> exportar() {
        return itens.stream()
                .map(Registros::exportarItem)
                .collect(toList());
    }

    private static Map<String, Object> exportarItem(Entidade item) {
        Campos dados = item.exportar();
        return dados.comoMapa();
    }
}
