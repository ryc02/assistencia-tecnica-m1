package com.assistencia.decorator;

import com.assistencia.model.valor.Dinheiro;

/** [Decorator - Aula 12] Decorator. */
public abstract class AdicionalDecorator implements ServicoOrcamento {
    protected final ServicoOrcamento servico;

    protected AdicionalDecorator(ServicoOrcamento servico) {
        this.servico = servico;
    }

    @Override
    public String descrever() {
        return servico.descrever();
    }

    @Override
    public Dinheiro calcularCusto() {
        return servico.calcularCusto();
    }
}
