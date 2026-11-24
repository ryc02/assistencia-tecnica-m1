package com.assistencia.service;

import com.assistencia.dados.Registros;
import com.assistencia.dao.ClienteDAO;
import com.assistencia.infra.GerenciadorTransacao;
import com.assistencia.model.cliente.Cliente;
import java.sql.Connection;

/** Consultas de clientes (por id e listagem). */
public class ConsultaCliente {
    private final ClienteDAO clientes;
    private final GerenciadorTransacao transacao;

    public ConsultaCliente(ClienteDAO clientes, GerenciadorTransacao transacao) {
        this.clientes = clientes;
        this.transacao = transacao;
    }

    public Cliente buscarPorId(Long id) throws Exception {
        return transacao.executar(conexao -> buscar(conexao, id));
    }

    private Cliente buscar(Connection conexao, Long id) throws Exception {
        return ClienteExistente.buscar(clientes, conexao, id);
    }

    public Registros<Cliente> listarTodos() throws Exception {
        return transacao.executar(clientes::listarTodos);
    }
}
