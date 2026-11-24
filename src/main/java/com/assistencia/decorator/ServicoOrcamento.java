package com.assistencia.decorator;

import com.assistencia.model.valor.Dinheiro;

/** [Decorator - Aula 12] Component. */
public interface ServicoOrcamento {
    String descrever();

    Dinheiro calcularCusto();
}
