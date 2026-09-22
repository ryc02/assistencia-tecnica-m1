package com.assistencia.dao;

import com.assistencia.model.Orcamento;

import java.sql.Connection;
import java.util.List;

/**
 * [Requisito 8: Padrão DAO]
 * Contrato de persistência para a entidade Orcamento.
 */
public interface OrcamentoDAO {
    Orcamento inserir(Connection conn, Orcamento orcamento) throws Exception;
    void atualizar(Connection conn, Orcamento orcamento) throws Exception;
    void excluir(Connection conn, Long id) throws Exception;
    Orcamento buscarPorId(Connection conn, Long id) throws Exception;
    Orcamento buscarPorIdParaUpdate(Connection conn, Long id) throws Exception;
    List<Orcamento> listarTodos(Connection conn) throws Exception;
    List<Orcamento> listarPorEquipamento(Connection conn, Long equipamentoId) throws Exception;
}
