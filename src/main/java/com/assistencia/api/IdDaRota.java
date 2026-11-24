package com.assistencia.api;

import com.assistencia.api.recurso.*;
import com.assistencia.dados.Campos;
import java.util.Map;

/** ID informado na URL (/api/recurso/{id}); vazio quando a operação é sobre a coleção. */
final class IdDaRota {
    private final Long valor;

    IdDaRota(Long valor) {
        this.valor = valor;
    }

    boolean vazio() {
        return valor == null;
    }

    Object consultar(RecursoConsultavel recurso) throws Exception {
        if (valor == null) {
            return recurso.listar();
        }
        return recurso.buscar(valor);
    }

    void atualizar(RecursoAtualizavel recurso, Campos corpo) throws Exception {
        recurso.atualizar(valor, corpo);
    }

    void excluir(RecursoExcluivel recurso) throws Exception {
        recurso.excluir(valor);
    }

    Map<String, Object> executar(RecursoComAcoes recurso, String acao, Campos corpo) throws Exception {
        return recurso.executarAcao(valor, acao, corpo);
    }
}
