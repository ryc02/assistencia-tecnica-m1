package com.assistencia.service;

import com.assistencia.dados.Registros;
import com.assistencia.infra.GerenciadorTransacao;
import com.assistencia.model.orcamento.Orcamento;
import java.sql.Connection;

/** Consultas de orçamentos (por id, todos e por equipamento). */
public class ConsultaOrcamento {
    private final RepositorioOrcamento repositorio;
    private final GerenciadorTransacao transacao;

    public ConsultaOrcamento(RepositorioOrcamento repositorio, GerenciadorTransacao transacao) {
        this.repositorio = repositorio;
        this.transacao = transacao;
    }

    public Orcamento buscarPorId(Long id) throws Exception {
        return transacao.executar(conexao -> existente(conexao, id));
    }

    public Registros<Orcamento> listarTodos() throws Exception {
        return transacao.executar(repositorio::listarTodos);
    }

    public Registros<Orcamento> listarPorEquipamento(Long equipamentoId) throws Exception {
        Existencia.exigirId(equipamentoId, "equipamento");
        return transacao.executar(conexao -> porEquipamento(conexao, equipamentoId));
    }

    private Orcamento existente(Connection conexao, Long id) throws Exception {
        return repositorio.existente(conexao, id);
    }

    private Registros<Orcamento> porEquipamento(Connection conexao, Long equipamentoId) throws Exception {
        return repositorio.listarPorEquipamento(conexao, equipamentoId);
    }
}
