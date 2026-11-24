package com.assistencia.model.ficha;

import com.assistencia.dados.*;
import com.assistencia.model.ordem.OrdemServico;
import com.assistencia.model.valor.Vinculo;
import static com.assistencia.model.ordem.ExigenciaDaOrdem.EDITAVEL;

/** Ordem de serviço inspecionada (relacionamento Ordem 1:1 Ficha) e o laudo da recepção. */
public final class Inspecao implements Exportavel {
    private final Vinculo<OrdemServico> ordemServico;
    private Laudo laudo;

    private Inspecao(Vinculo<OrdemServico> ordemServico, Laudo laudo) {
        this.ordemServico = ordemServico;
        this.laudo = laudo;
    }

    public static Inspecao de(Campos dados) {
        Vinculo<OrdemServico> ordem = Vinculo.obrigatorio(dados, "ordemServico",
                "Ordem de serviço é obrigatória para a ficha técnica.");
        return new Inspecao(ordem, Laudo.de(dados));
    }

    /** O laudo só pode mudar enquanto a ordem estiver ABERTA ou EM_ANDAMENTO. */
    void atualizar(Campos dados) {
        ordemServico.executar(Inspecao::exigirEditavel);
        laudo = Laudo.de(dados);
    }

    @Override
    public void exportar(Campos destino) {
        ordemServico.exportar(destino);
        laudo.exportar(destino);
    }

    private static void exigirEditavel(OrdemServico ordem) {
        ordem.exigir(EDITAVEL);
    }
}
