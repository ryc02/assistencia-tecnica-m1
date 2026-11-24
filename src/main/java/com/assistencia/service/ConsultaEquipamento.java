package com.assistencia.service;

import com.assistencia.dados.Registros;
import com.assistencia.infra.GerenciadorTransacao;
import com.assistencia.model.equipamento.Equipamento;
import java.sql.Connection;

/** Consultas de equipamentos (por id, todos e por cliente). */
public class ConsultaEquipamento {
    private final RepositorioEquipamento repositorio;
    private final GerenciadorTransacao transacao;

    public ConsultaEquipamento(RepositorioEquipamento repositorio, GerenciadorTransacao transacao) {
        this.repositorio = repositorio;
        this.transacao = transacao;
    }

    public Equipamento buscarPorId(Long id) throws Exception {
        return transacao.executar(conexao -> existente(conexao, id));
    }

    public Registros<Equipamento> listarTodos() throws Exception {
        return transacao.executar(repositorio::listarTodos);
    }

    public Registros<Equipamento> listarPorCliente(Long clienteId) throws Exception {
        Existencia.exigirId(clienteId, "cliente");
        return transacao.executar(conexao -> porCliente(conexao, clienteId));
    }

    private Equipamento existente(Connection conexao, Long id) throws Exception {
        return repositorio.existente(conexao, id);
    }

    private Registros<Equipamento> porCliente(Connection conexao, Long clienteId) throws Exception {
        return repositorio.listarPorCliente(conexao, clienteId);
    }
}
