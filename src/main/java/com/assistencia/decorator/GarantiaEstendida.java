package com.assistencia.decorator;

import com.assistencia.model.valor.Dinheiro;
import static com.assistencia.decorator.TipoAdicional.GARANTIA_ESTENDIDA;

/**

 * [Decorator - Aula 12] ConcreteDecorator: Garantia estendida do serviço. Acrescenta sua descrição e seu valor

 * ao serviço decorado (como Leite, Chocolate e Chantilly).

 */
public class GarantiaEstendida extends AdicionalDecorator {
    public GarantiaEstendida(ServicoOrcamento servico) {
        super(servico);
    }

    @Override
    public String descrever() {
        String anterior = servico.descrever();
        return GARANTIA_ESTENDIDA.anexarDescricao(anterior);
    }

    @Override
    public Dinheiro calcularCusto() {
        Dinheiro anterior = servico.calcularCusto();
        return GARANTIA_ESTENDIDA.acrescentarValor(anterior);
    }
}
