package com.assistencia.model.ordem;

import com.assistencia.dados.*;
import com.assistencia.model.orcamento.Orcamento;
import com.assistencia.model.valor.Vinculo;

/** Orçamento aprovado que originou a ordem (relacionamento Orçamento 1:1 Ordem) e o andamento. */
public final class Execucao implements Exportavel {
    private final Vinculo<Orcamento> orcamento;
    private final Andamento andamento;

    public Execucao(Vinculo<Orcamento> orcamento, Andamento andamento) {
        this.orcamento = orcamento;
        this.andamento = andamento;
    }

    public static Execucao de(Campos dados) {
        Vinculo<Orcamento> orcamento = Vinculo.obrigatorio(dados, "orcamento",
                "Orçamento é obrigatório para gerar a ordem de serviço.");
        Andamento andamento = Andamento.de(dados);
        return new Execucao(orcamento, andamento);
    }

    @Override
    public void exportar(Campos destino) {
        orcamento.exportar(destino);
        andamento.exportar(destino);
    }

    void validarPrevisao(Planejamento planejamento) {
        andamento.validarPrevisao(planejamento);
    }

    void mudar(MudancaDeOrdem mudanca) {
        andamento.mudar(mudanca);
    }

    void exigir(ExigenciaDaOrdem exigencia) {
        andamento.exigir(exigencia);
    }
}
