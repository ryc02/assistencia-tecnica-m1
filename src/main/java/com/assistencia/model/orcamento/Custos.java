package com.assistencia.model.orcamento;

import com.assistencia.dados.*;
import com.assistencia.decorator.*;
import com.assistencia.model.valor.Dinheiro;

/** Valores de peças e de mão de obra (não negativos). */
public final class Custos implements Exportavel {
    private final Dinheiro pecas;
    private final Dinheiro maoDeObra;

    public Custos(Dinheiro pecas, Dinheiro maoDeObra) {
        this.pecas = pecas;
        this.maoDeObra = maoDeObra;
    }

    public static Custos de(Campos dados) {
        Dinheiro pecas = Dinheiro.naoNegativo(dados, "valorPecas", "Valor de peças");
        Dinheiro maoDeObra = Dinheiro.naoNegativo(dados, "valorMaoDeObra", "Valor de mão de obra");
        return new Custos(pecas, maoDeObra);
    }

    @Override
    public void exportar(Campos destino) {
        pecas.exportarComo("valorPecas", destino);
        maoDeObra.exportarComo("valorMaoDeObra", destino);
    }

    /** [Decorator] ConcreteComponent com peças + mão de obra. */
    ServicoOrcamento comoServico() {
        return new ServicoBase(pecas, maoDeObra);
    }
}
