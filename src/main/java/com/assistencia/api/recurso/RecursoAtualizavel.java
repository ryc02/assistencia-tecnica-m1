package com.assistencia.api.recurso;

import com.assistencia.dados.Campos;

/** [LSP/ISP] PUT /api/{recurso}/{id}: substitui os dados do recurso. */
public interface RecursoAtualizavel {
    void atualizar(Long id, Campos corpo) throws Exception;
}
