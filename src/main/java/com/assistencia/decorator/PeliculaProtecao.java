package com.assistencia.decorator;

import com.assistencia.model.valor.Dinheiro;
import static com.assistencia.decorator.TipoAdicional.PELICULA_PROTECAO;

/**

 * [Decorator - Aula 12] ConcreteDecorator: Aplicação de película de proteção de tela. Acrescenta sua descrição

 * e seu valor ao serviço decorado (como Leite, Chocolate e Chantilly).

 */
public class PeliculaProtecao extends AdicionalDecorator {
    public PeliculaProtecao(ServicoOrcamento servico) {
        super(servico);
    }

    @Override
    public String descrever() {
        String anterior = servico.descrever();
        return PELICULA_PROTECAO.anexarDescricao(anterior);
    }

    @Override
    public Dinheiro calcularCusto() {
        Dinheiro anterior = servico.calcularCusto();
        return PELICULA_PROTECAO.acrescentarValor(anterior);
    }
}
