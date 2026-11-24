package com.assistencia.model;

import com.assistencia.exception.ConflictException;
import java.util.Set;

/** Verificações de status usadas pelas mudanças de estado (o %s da mensagem recebe o status atual). */
public final class RegrasDeStatus {
    private RegrasDeStatus() { }

    public static <E extends Enum<E>> void exigirIgual(E atual, E esperado, String mensagem) {
        if (atual != esperado) {
            throw new ConflictException(String.format(mensagem, atual));
        }
    }

    public static <E extends Enum<E>> void proibir(E atual, Set<E> proibidos, String mensagem) {
        if (proibidos.contains(atual)) {
            throw new ConflictException(String.format(mensagem, atual));
        }
    }
}
