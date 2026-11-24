package com.assistencia.model.orcamento;

import com.assistencia.dados.*;
import com.assistencia.decorator.ServicoOrcamento;
import com.assistencia.model.valor.*;

/** Percentual de desconto e adicionais escolhidos. */
public final class Condicoes implements Exportavel {
    private final Percentual desconto;
    private final Adicionais adicionais;

    public Condicoes(Percentual desconto, Adicionais adicionais) {
        this.desconto = desconto;
        this.adicionais = adicionais;
    }

    public static Condicoes de(Campos dados) {
        Percentual desconto = Percentual.de(dados, "percentualDesconto");
        Adicionais adicionais = Adicionais.de(dados);
        return new Condicoes(desconto, adicionais);
    }

    @Override
    public void exportar(Campos destino) {
        desconto.exportarComo("percentualDesconto", destino);
        adicionais.exportar(destino);
    }

    ServicoOrcamento decorar(ServicoOrcamento servico) {
        return adicionais.decorar(servico);
    }

    Dinheiro aplicarDesconto(Dinheiro custo) {
        return custo.descontar(desconto);
    }
}
