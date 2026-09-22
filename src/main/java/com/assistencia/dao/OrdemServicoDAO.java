package com.assistencia.dao;

import com.assistencia.model.OrdemServico;

import java.sql.Connection;
import java.util.List;

/**
 * [Requisito 8: Padrão DAO]
 * Contrato de persistência para a entidade OrdemServico.
 */
public interface OrdemServicoDAO {
    OrdemServico inserir(Connection conn, OrdemServico ordemServico) throws Exception;
    void atualizar(Connection conn, OrdemServico ordemServico) throws Exception;
    void excluir(Connection conn, Long id) throws Exception;
    OrdemServico buscarPorId(Connection conn, Long id) throws Exception;
    OrdemServico buscarPorOrcamentoId(Connection conn, Long orcamentoId) throws Exception;
    OrdemServico buscarPorIdParaUpdate(Connection conn, Long id) throws Exception;
    List<OrdemServico> listarTodas(Connection conn) throws Exception;
    List<OrdemServico> listarPorOrcamento(Connection conn, Long orcamentoId) throws Exception;
}
