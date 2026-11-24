package com.assistencia.model.orcamento;

import com.assistencia.dados.*;
import com.assistencia.decorator.ServicoOrcamento;
import com.assistencia.model.valor.Dinheiro;

/** Proposta financeira: custos e condições; o total é o serviço decorado com os adicionais, menos o desconto. */
public final class Proposta implements Exportavel {
    private final Custos custos;
    private final Condicoes condicoes;

    private Proposta(Custos custos, Condicoes condicoes) {
        this.custos = custos;
        this.condicoes = condicoes;
    }

    public static Proposta de(Campos dados) {
        Custos custos = Custos.de(dados);
        return new Proposta(custos, Condicoes.de(dados));
    }

    /** Monta o serviço decorado, como new Leite(new Chocolate(new Cafe())) na Cafeteria. */
    ServicoOrcamento montarServico() {
        ServicoOrcamento base = custos.comoServico();
        return condicoes.decorar(base);
    }

    Dinheiro calcularTotal() {
        ServicoOrcamento servico = montarServico();
        Dinheiro custo = servico.calcularCusto();
        return condicoes.aplicarDesconto(custo);
    }

    @Override
    public void exportar(Campos destino) {
        custos.exportar(destino);
        condicoes.exportar(destino);
        Dinheiro total = calcularTotal();
        total.exportarComo("valorTotal", destino);
        ServicoOrcamento servico = montarServico();
        String descricao = servico.descrever();
        destino.com("descricaoServico", descricao);
    }
}
