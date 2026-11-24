package com.assistencia.service;

import com.assistencia.dados.Campos;
import com.assistencia.dao.OrcamentoDAO;
import com.assistencia.model.orcamento.Orcamento;
import com.assistencia.model.ordem.OrdemServico;
import java.sql.Connection;

/** Reúne o OrcamentoDAO e o registro de Ordem + Ficha usados na aprovação do orçamento. */
public class RepositorioAprovacao {
    private final OrcamentoDAO orcamentos;
    private final RegistroDeAtendimento atendimentos;

    public RepositorioAprovacao(OrcamentoDAO orcamentos, RegistroDeAtendimento atendimentos) {
        this.orcamentos = orcamentos;
        this.atendimentos = atendimentos;
    }

    Orcamento orcamentoParaAtualizar(Connection conexao, Long id) throws Exception {
        return OrcamentoBloqueado.buscar(orcamentos, conexao, id);
    }

    Orcamento orcamentoDaOrdem(Connection conexao, OrdemServico ordem) throws Exception {
        Campos dados = ordem.exportar();
        return orcamentoParaAtualizar(conexao, dados.inteiroLongo("orcamentoId"));
    }

    void atualizar(Connection conexao, Orcamento orcamento) throws Exception {
        orcamentos.atualizar(conexao, orcamento);
    }

    OrdemServico ordemCompleta(Connection conexao, Long orcamentoId) throws Exception {
        return atendimentos.ordemCompleta(conexao, orcamentoId);
    }

    OrdemServico registrar(Connection conexao, Orcamento orcamento, Campos dadosOrdem, Campos dadosFicha)
            throws Exception {
        return atendimentos.registrar(conexao, orcamento, dadosOrdem, dadosFicha);
    }

    OrdemServico ordemParaAtualizar(Connection conexao, Long ordemId) throws Exception {
        return atendimentos.ordemParaAtualizar(conexao, ordemId);
    }

    void remover(Connection conexao, Long ordemId) throws Exception {
        atendimentos.remover(conexao, ordemId);
    }
}
