package com.assistencia.decorator;

import com.assistencia.model.valor.Dinheiro;
import static com.assistencia.decorator.TipoAdicional.LIMPEZA_INTERNA;

/**

 * [Decorator - Aula 12] ConcreteDecorator: Limpeza interna e troca de pasta térmica. Acrescenta sua descrição e

 * seu valor ao serviço decorado (como Leite, Chocolate e Chantilly).

 */
public class LimpezaInterna extends AdicionalDecorator {
    public LimpezaInterna(ServicoOrcamento servico) {
        super(servico);
    }

    @Override
    public String descrever() {
        String anterior = servico.descrever();
        return LIMPEZA_INTERNA.anexarDescricao(anterior);
    }

    @Override
    public Dinheiro calcularCusto() {
        Dinheiro anterior = servico.calcularCusto();
        return LIMPEZA_INTERNA.acrescentarValor(anterior);
    }
}
