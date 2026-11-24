package com.assistencia.api.recurso;

/** [LSP/ISP] DELETE /api/{recurso}/{id}: HTTP 204 em caso de sucesso. */
public interface RecursoExcluivel {
    void excluir(Long id) throws Exception;
}
