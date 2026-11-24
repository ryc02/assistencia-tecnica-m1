package com.assistencia.service;

import com.assistencia.dao.OrdemServicoDAO;
import com.assistencia.model.ordem.OrdemServico;
import java.sql.Connection;

/** Busca uma ordem de serviço que precisa existir (id obrigatório e 404 quando não encontrada). */
final class OrdemExistente {
    private OrdemExistente() { }

    static OrdemServico buscar(OrdemServicoDAO ordens, Connection conexao, Long id) throws Exception {
        Existencia.exigirId(id, "ordem de serviço");
        OrdemServico ordem = ordens.buscarPorId(conexao, id);
        return Existencia.exigir(ordem, "Ordem de Serviço não encontrada com ID: " + id);
    }

    static OrdemServico paraAtualizar(OrdemServicoDAO ordens, Connection conexao, Long id) throws Exception {
        Existencia.exigirId(id, "ordem de serviço");
        OrdemServico ordem = ordens.buscarPorIdParaUpdate(conexao, id);
        return Existencia.exigir(ordem, "Ordem de Serviço não encontrada com ID: " + id);
    }
}
