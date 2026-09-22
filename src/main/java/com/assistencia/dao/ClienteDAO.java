package com.assistencia.dao;

import com.assistencia.model.Cliente;

import java.sql.Connection;
import java.util.List;

/**
 * [Requisito 8: Padrão DAO]
 * Contrato de persistência para a entidade Cliente.
 */
public interface ClienteDAO {
    Cliente inserir(Connection conn, Cliente cliente) throws Exception;
    void atualizar(Connection conn, Cliente cliente) throws Exception;
    void excluir(Connection conn, Long id) throws Exception;
    Cliente buscarPorId(Connection conn, Long id) throws Exception;
    Cliente buscarPorCpf(Connection conn, String cpf) throws Exception;
    List<Cliente> listarTodos(Connection conn) throws Exception;
}
