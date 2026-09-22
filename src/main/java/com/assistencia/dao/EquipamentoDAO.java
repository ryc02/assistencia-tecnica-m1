package com.assistencia.dao;

import com.assistencia.model.Equipamento;

import java.sql.Connection;
import java.util.List;

/**
 * [Requisito 8: Padrão DAO]
 * Contrato de persistência para a entidade Equipamento.
 */
public interface EquipamentoDAO {
    Equipamento inserir(Connection conn, Equipamento equipamento) throws Exception;
    void atualizar(Connection conn, Equipamento equipamento) throws Exception;
    void excluir(Connection conn, Long id) throws Exception;
    Equipamento buscarPorId(Connection conn, Long id) throws Exception;
    List<Equipamento> listarTodos(Connection conn) throws Exception;
    List<Equipamento> listarPorCliente(Connection conn, Long clienteId) throws Exception;
}
