package com.assistencia.dao;

import com.assistencia.dados.Registros;
import com.assistencia.model.orcamento.Orcamento;
import java.sql.Connection;

/** [DAO - Aula 04] Contrato de persistência do Orçamento. */
public interface OrcamentoDAO {
    Orcamento inserir(Connection conexao, Orcamento orcamento) throws Exception;

    void atualizar(Connection conexao, Orcamento orcamento) throws Exception;

    void excluir(Connection conexao, Long id) throws Exception;

    Orcamento buscarPorId(Connection conexao, Long id) throws Exception;

    Orcamento buscarPorIdParaUpdate(Connection conexao, Long id) throws Exception;

    Registros<Orcamento> listarTodos(Connection conexao) throws Exception;

    Registros<Orcamento> listarPorEquipamento(Connection conexao, Long equipamentoId) throws Exception;

    boolean possuiOrdemDeServico(Connection conexao, Long id) throws Exception;
}
