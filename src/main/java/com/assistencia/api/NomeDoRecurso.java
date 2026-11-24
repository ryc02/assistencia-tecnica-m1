package com.assistencia.api;

import com.assistencia.exception.NotFoundException;
import com.assistencia.factory.Registro;
import static com.assistencia.factory.RecursoFactory.CONSULTAVEIS;

/** Nome do recurso REST na URL (/api/{nome}/...), usado para localizar o recurso registrado. */
final class NomeDoRecurso {
    private final String nome;

    NomeDoRecurso(String nome) {
        this.nome = nome;
    }

    boolean eh(String esperado) {
        return nome.equals(esperado);
    }

    void exigirExistente() {
        if (!CONSULTAVEIS.possui(nome)) {
            throw new NotFoundException("Recurso não encontrado: /api/" + nome);
        }
    }

    /** Recurso registrado que suporta a operação (405 quando não suporta - ISP). */
    <T> T suportado(Registro<T> registro) {
        return registro.exigir(nome);
    }
}
