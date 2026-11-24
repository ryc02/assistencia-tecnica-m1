package com.assistencia.model.orcamento;

import com.assistencia.dados.Conversor;
import java.util.*;
import static java.util.stream.Collectors.toList;

/** Lê os nomes dos adicionais a partir de uma lista ou de um texto separado por vírgulas. */
final class NomesDeAdicionais {
    private NomesDeAdicionais() { }

    static List<String> ler(Object valor) {
        if (valor instanceof List) {
            return converterLista((List<?>) valor);
        }
        if (!Conversor.preenchido(valor)) {
            return List.of();
        }
        String texto = Conversor.texto(valor);
        String[] partes = texto.split(",");
        return converterLista(Arrays.asList(partes));
    }

    private static List<String> converterLista(List<?> itens) {
        return itens.stream()
                .map(Conversor::texto)
                .collect(toList());
    }
}
