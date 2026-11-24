package com.assistencia.service;

import com.assistencia.dao.*;
import com.assistencia.model.orcamento.Orcamento;
import java.sql.Connection;

/** Reúne os DAOs usados pelos casos de uso de orçamento (Orçamento e o Equipamento avaliado). */
public class RepositorioOrcamento extends ConsultasDeOrcamento {
    public RepositorioOrcamento(OrcamentoDAO orcamentos, EquipamentoDAO equipamentos) {
        super(orcamentos, equipamentos);
    }

    Orcamento paraAtualizar(Connection conexao, Long id) throws Exception {
        return OrcamentoBloqueado.buscar(orcamentos, conexao, id);
    }

    Orcamento inserir(Connection conexao, Orcamento orcamento) throws Exception {
        return orcamentos.inserir(conexao, orcamento);
    }

    void atualizar(Connection conexao, Orcamento orcamento) throws Exception {
        orcamentos.atualizar(conexao, orcamento);
    }

    void excluir(Connection conexao, Long id) throws Exception {
        orcamentos.excluir(conexao, id);
    }

    boolean possuiOrdemDeServico(Connection conexao, Long id) throws Exception {
        return orcamentos.possuiOrdemDeServico(conexao, id);
    }
}
