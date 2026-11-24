package com.assistencia.dao;

import com.assistencia.dados.Registros;
import com.assistencia.model.equipamento.Equipamento;
import java.sql.Connection;

/** [DAO - Aula 04] Contrato de persistência do Equipamento. */
public interface EquipamentoDAO {
    Equipamento inserir(Connection conexao, Equipamento equipamento) throws Exception;

    void atualizar(Connection conexao, Equipamento equipamento) throws Exception;

    void excluir(Connection conexao, Long id) throws Exception;

    Equipamento buscarPorId(Connection conexao, Long id) throws Exception;

    Registros<Equipamento> listarTodos(Connection conexao) throws Exception;

    Registros<Equipamento> listarPorCliente(Connection conexao, Long clienteId) throws Exception;

    Long contarOrcamentos(Connection conexao, Long equipamentoId) throws Exception;
}
