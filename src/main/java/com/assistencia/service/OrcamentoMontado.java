package com.assistencia.service;

import com.assistencia.builder.OrcamentoBuilder;
import com.assistencia.dados.Campos;
import com.assistencia.model.equipamento.Equipamento;
import com.assistencia.model.orcamento.Orcamento;
import java.sql.Connection;
import java.time.LocalDateTime;
import static com.assistencia.model.orcamento.ExigenciaDoOrcamento.PENDENTE_PARA_ALTERAR;
import static com.assistencia.model.orcamento.StatusOrcamento.PENDENTE;

/** Monta o Orçamento (via Builder) sempre PENDENTE; na alteração exige que o atual ainda esteja PENDENTE. */
final class OrcamentoMontado {
    private OrcamentoMontado() { }

    static Orcamento novo(RepositorioOrcamento repositorio, Connection conexao, Campos dados) throws Exception {
        Equipamento equipamento = repositorio.equipamentoInformado(conexao, dados);
        return montar(dados, null, equipamento, null);
    }

    static Orcamento atualizado(RepositorioOrcamento repositorio, Connection conexao, Long id, Campos dados)
            throws Exception {
        Orcamento existente = repositorio.existente(conexao, id);
        existente.exigir(PENDENTE_PARA_ALTERAR);
        Equipamento equipamento = repositorio.equipamentoInformado(conexao, dados);
        Campos atuais = existente.exportar();
        return montar(dados, id, equipamento, atuais.dataHora("dataCriacao"));
    }

    private static Orcamento montar(Campos dados, Long id, Equipamento equipamento, LocalDateTime dataCriacao) {
        return new OrcamentoBuilder()
                .dados(dados)
                .id(id)
                .equipamento(equipamento)
                .status(PENDENTE)
                .dataCriacao(dataCriacao)
                .build();
    }
}
