package com.assistencia.dao.jdbc;

import com.assistencia.builder.OrdemServicoBuilder;
import com.assistencia.dados.Campos;
import com.assistencia.dao.OrcamentoDAO;
import com.assistencia.model.orcamento.Orcamento;
import com.assistencia.model.ordem.OrdemServico;
import java.sql.Connection;

/** Monta o OrdemServico a partir de uma linha da tabela, carregando o Orcamento relacionado pelo seu DAO. */
final class MontagemDeOrdemServico implements MontagemDeEntidade<OrdemServico> {
    private final OrcamentoDAO orcamentos;

    MontagemDeOrdemServico(OrcamentoDAO orcamentos) {
        this.orcamentos = orcamentos;
    }

    @Override
    public OrdemServico montar(Connection conexao, Campos linha) throws Exception {
        Long orcamentoId = linha.inteiroLongo("orcamentoId");
        Orcamento orcamento = orcamentos.buscarPorId(conexao, orcamentoId);
        return new OrdemServicoBuilder()
                .dados(linha)
                .orcamento(orcamento)
                .build();
    }
}
