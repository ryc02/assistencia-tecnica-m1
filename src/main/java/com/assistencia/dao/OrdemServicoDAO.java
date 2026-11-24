package com.assistencia.dao;

import com.assistencia.dados.Registros;
import com.assistencia.model.ordem.OrdemServico;
import java.sql.Connection;

/** [DAO - Aula 04] Contrato de persistência da Ordem de Serviço. */
public interface OrdemServicoDAO {
    OrdemServico inserir(Connection conexao, OrdemServico ordemServico) throws Exception;

    void atualizar(Connection conexao, OrdemServico ordemServico) throws Exception;

    void excluir(Connection conexao, Long id) throws Exception;

    OrdemServico buscarPorId(Connection conexao, Long id) throws Exception;

    OrdemServico buscarPorIdParaUpdate(Connection conexao, Long id) throws Exception;

    OrdemServico buscarPorOrcamentoId(Connection conexao, Long orcamentoId) throws Exception;

    Registros<OrdemServico> listarTodas(Connection conexao) throws Exception;
}
