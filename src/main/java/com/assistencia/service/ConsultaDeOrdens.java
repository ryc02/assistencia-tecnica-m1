package com.assistencia.service;

import com.assistencia.dados.Registros;
import com.assistencia.infra.GerenciadorTransacao;
import com.assistencia.model.ordem.OrdemServico;
import java.sql.Connection;

/** Consultas de ordens de serviço; a subclasse ConsultaAtendimento acrescenta as fichas técnicas. */
abstract class ConsultaDeOrdens {
    protected final LeituraDeAtendimento leitura;
    protected final GerenciadorTransacao transacao;

    ConsultaDeOrdens(LeituraDeAtendimento leitura, GerenciadorTransacao transacao) {
        this.leitura = leitura;
        this.transacao = transacao;
    }

    public OrdemServico ordem(Long id) throws Exception {
        return transacao.executar(conexao -> leituraDaOrdem(conexao, id));
    }

    public OrdemServico ordemDoOrcamento(Long orcamentoId) throws Exception {
        return transacao.executar(conexao -> leituraDaOrdemDoOrcamento(conexao, orcamentoId));
    }

    public Registros<OrdemServico> listarOrdens() throws Exception {
        return transacao.executar(leitura::listarOrdens);
    }

    private OrdemServico leituraDaOrdem(Connection conexao, Long id) throws Exception {
        return leitura.ordem(conexao, id);
    }

    private OrdemServico leituraDaOrdemDoOrcamento(Connection conexao, Long orcamentoId) throws Exception {
        return leitura.ordemDoOrcamento(conexao, orcamentoId);
    }
}
