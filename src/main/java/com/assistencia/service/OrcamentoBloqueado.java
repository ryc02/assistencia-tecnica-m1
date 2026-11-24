package com.assistencia.service;

import com.assistencia.dao.OrcamentoDAO;
import com.assistencia.model.orcamento.Orcamento;
import java.sql.Connection;

/** Busca o orçamento bloqueando a linha (SELECT ... FOR UPDATE) contra alterações simultâneas. */
final class OrcamentoBloqueado {
    private OrcamentoBloqueado() { }

    static Orcamento buscar(OrcamentoDAO orcamentos, Connection conexao, Long id) throws Exception {
        Existencia.exigirId(id, "orçamento");
        Orcamento orcamento = orcamentos.buscarPorIdParaUpdate(conexao, id);
        return Existencia.exigir(orcamento, "Orçamento não encontrado com ID: " + id);
    }
}
