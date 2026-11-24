package com.assistencia.service;

import com.assistencia.dados.Campos;
import com.assistencia.dao.ClienteDAO;
import com.assistencia.infra.GerenciadorTransacao;
import com.assistencia.model.cliente.Cliente;
import java.sql.Connection;

/** [RF01] Cadastro, alteração e exclusão de clientes. */
public class ClienteService {
    private final ClienteDAO clientes;
    private final GerenciadorTransacao transacao;

    public ClienteService(ClienteDAO clientes, GerenciadorTransacao transacao) {
        this.clientes = clientes;
        this.transacao = transacao;
    }

    public Cliente cadastrar(Campos dados) throws Exception {
        Cliente cliente = ClienteMontado.novo(dados);
        return transacao.executar(conexao -> inserir(conexao, cliente));
    }

    public void atualizar(Long id, Campos dados) throws Exception {
        transacao.executar(conexao -> atualizar(conexao, id, dados));
    }

    public void excluir(Long id) throws Exception {
        transacao.executar(conexao -> excluir(conexao, id));
    }

    private Cliente inserir(Connection conexao, Cliente cliente) throws Exception {
        CpfUnico.exigir(clientes, conexao, cliente, "Já existe um cliente cadastrado com o CPF informado.");
        return clientes.inserir(conexao, cliente);
    }

    private Void atualizar(Connection conexao, Long id, Campos dados) throws Exception {
        Cliente cliente = ClienteMontado.atualizado(clientes, conexao, id, dados);
        CpfUnico.exigir(clientes, conexao, cliente, "O CPF informado já pertence a outro cliente.");
        clientes.atualizar(conexao, cliente);
        return null;
    }

    private Void excluir(Connection conexao, Long id) throws Exception {
        ClienteExistente.exigirSemEquipamentos(clientes, conexao, id);
        clientes.excluir(conexao, id);
        return null;
    }
}
