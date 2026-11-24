package com.assistencia.unitario;

import com.assistencia.Dados;
import com.assistencia.builder.*;
import com.assistencia.dados.Campos;
import com.assistencia.model.equipamento.Equipamento;
import com.assistencia.model.ficha.FichaTecnica;
import com.assistencia.model.orcamento.*;
import com.assistencia.model.ordem.*;
import static com.assistencia.model.orcamento.StatusOrcamento.APROVADO;
import static com.assistencia.unitario.FixturesCadastro.cliente;

/** Orçamentos, ordens de serviço e fichas técnicas fictícios usados na etapa Arrange dos testes. */
public final class FixturesAtendimento {
    private FixturesAtendimento() { }

    public static Campos dadosDeOrcamento() {
        return Dados.de("equipamentoId", 1L, "descricaoProblema", "Não liga", "diagnostico", "Fonte queimada",
                "valorPecas", "100.00", "valorMaoDeObra", "50.00", "percentualDesconto", "0");
    }

    public static Orcamento orcamento(Long id, StatusOrcamento status) {
        Equipamento equipamento = FixturesCadastro.equipamento(1L, cliente(1L));
        return new OrcamentoBuilder()
                .dados(dadosDeOrcamento())
                .id(id)
                .equipamento(equipamento)
                .status(status)
                .build();
    }

    public static OrdemServico ordem(Long id, StatusOrdem status) {
        return new OrdemServicoBuilder()
                .id(id)
                .orcamento(orcamento(1L, APROVADO))
                .responsavel("Técnico Lucas")
                .status(status)
                .build();
    }

    public static FichaTecnica ficha(Long id, StatusOrdem statusDaOrdem) {
        return new FichaTecnicaBuilder()
                .id(id)
                .ordemServico(ordem(1L, statusDaOrdem))
                .build();
    }
}
