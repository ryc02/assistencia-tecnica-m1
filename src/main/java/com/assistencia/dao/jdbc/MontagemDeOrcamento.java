package com.assistencia.dao.jdbc;

import com.assistencia.builder.OrcamentoBuilder;
import com.assistencia.dados.Campos;
import com.assistencia.dao.EquipamentoDAO;
import com.assistencia.model.equipamento.Equipamento;
import com.assistencia.model.orcamento.Orcamento;
import java.sql.Connection;

/** Monta o Orcamento a partir de uma linha da tabela, carregando o Equipamento relacionado pelo seu DAO. */
final class MontagemDeOrcamento implements MontagemDeEntidade<Orcamento> {
    private final EquipamentoDAO equipamentos;

    MontagemDeOrcamento(EquipamentoDAO equipamentos) {
        this.equipamentos = equipamentos;
    }

    @Override
    public Orcamento montar(Connection conexao, Campos linha) throws Exception {
        Long equipamentoId = linha.inteiroLongo("equipamentoId");
        Equipamento equipamento = equipamentos.buscarPorId(conexao, equipamentoId);
        return new OrcamentoBuilder()
                .dados(linha)
                .equipamento(equipamento)
                .build();
    }
}
