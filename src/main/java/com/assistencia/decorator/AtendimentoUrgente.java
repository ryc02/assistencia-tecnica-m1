package com.assistencia.decorator;

import com.assistencia.model.valor.Dinheiro;
import static com.assistencia.decorator.TipoAdicional.ATENDIMENTO_URGENTE;

/**

 * [Decorator - Aula 12] ConcreteDecorator: Prioridade de atendimento (urgência). Acrescenta sua descrição e seu

 * valor ao serviço decorado (como Leite, Chocolate e Chantilly).

 */
public class AtendimentoUrgente extends AdicionalDecorator {
    public AtendimentoUrgente(ServicoOrcamento servico) {
        super(servico);
    }

    @Override
    public String descrever() {
        String anterior = servico.descrever();
        return ATENDIMENTO_URGENTE.anexarDescricao(anterior);
    }

    @Override
    public Dinheiro calcularCusto() {
        Dinheiro anterior = servico.calcularCusto();
        return ATENDIMENTO_URGENTE.acrescentarValor(anterior);
    }
}
