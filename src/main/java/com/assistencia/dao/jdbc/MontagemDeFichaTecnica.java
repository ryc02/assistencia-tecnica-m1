package com.assistencia.dao.jdbc;

import com.assistencia.builder.FichaTecnicaBuilder;
import com.assistencia.dados.Campos;
import com.assistencia.dao.OrdemServicoDAO;
import com.assistencia.model.ficha.FichaTecnica;
import com.assistencia.model.ordem.OrdemServico;
import java.sql.Connection;

/** Monta o FichaTecnica a partir de uma linha da tabela, carregando o OrdemServico relacionado pelo seu DAO. */
final class MontagemDeFichaTecnica implements MontagemDeEntidade<FichaTecnica> {
    private final OrdemServicoDAO ordemServicos;

    MontagemDeFichaTecnica(OrdemServicoDAO ordemServicos) {
        this.ordemServicos = ordemServicos;
    }

    @Override
    public FichaTecnica montar(Connection conexao, Campos linha) throws Exception {
        Long ordemServicoId = linha.inteiroLongo("ordemServicoId");
        OrdemServico ordemServico = ordemServicos.buscarPorId(conexao, ordemServicoId);
        return new FichaTecnicaBuilder()
                .dados(linha)
                .ordemServico(ordemServico)
                .build();
    }
}
