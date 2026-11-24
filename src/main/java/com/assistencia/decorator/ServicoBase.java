package com.assistencia.decorator;

import com.assistencia.model.valor.Dinheiro;

/** [Decorator - Aula 12] ConcreteComponent. */
public class ServicoBase implements ServicoOrcamento {
    private final Dinheiro pecas;
    private final Dinheiro maoDeObra;

    public ServicoBase(Dinheiro pecas, Dinheiro maoDeObra) {
        this.pecas = pecas;
        this.maoDeObra = maoDeObra;
    }

    @Override
    public String descrever() {
        return "Serviço técnico";
    }

    @Override
    public Dinheiro calcularCusto() {
        return pecas.somar(maoDeObra);
    }
}
