package com.assistencia.api;

import com.assistencia.api.recurso.RecursoComAcoes;
import com.assistencia.dados.Campos;
import java.util.Map;

/** Ação informada na URL (/api/recurso/id/{acao}), como aprovar, recusar, iniciar ou concluir. */
final class AcaoDaRota {
    private final String nome;

    AcaoDaRota(String nome) {
        this.nome = nome;
    }

    boolean vazia() {
        return nome == null;
    }

    Map<String, Object> executar(RecursoComAcoes recurso, IdDaRota id, Campos corpo) throws Exception {
        return id.executar(recurso, nome, corpo);
    }
}
