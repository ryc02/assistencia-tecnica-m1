package com.assistencia.dao;

import com.assistencia.dados.Registros;
import com.assistencia.model.cliente.Cliente;
import java.sql.Connection;

/** [DAO - Aula 04] Contrato de persistência do Cliente. */
public interface ClienteDAO {
    Cliente inserir(Connection conexao, Cliente cliente) throws Exception;

    void atualizar(Connection conexao, Cliente cliente) throws Exception;

    void excluir(Connection conexao, Long id) throws Exception;

    Cliente buscarPorId(Connection conexao, Long id) throws Exception;

    Cliente buscarPorCpf(Connection conexao, String cpf) throws Exception;

    Registros<Cliente> listarTodos(Connection conexao) throws Exception;

    Long contarEquipamentos(Connection conexao, Long clienteId) throws Exception;
}
