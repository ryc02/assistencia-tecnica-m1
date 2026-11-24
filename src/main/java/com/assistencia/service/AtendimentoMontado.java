package com.assistencia.service;

import com.assistencia.builder.*;
import com.assistencia.dados.Campos;
import com.assistencia.model.ficha.FichaTecnica;
import com.assistencia.model.orcamento.Orcamento;
import com.assistencia.model.ordem.OrdemServico;
import static com.assistencia.model.ordem.StatusOrdem.ABERTA;

/** Monta (via Builder) a nova Ordem de Serviço ABERTA e a sua Ficha Técnica gerada na aprovação. */
final class AtendimentoMontado {
    private AtendimentoMontado() { }

    static OrdemServico novaOrdem(Campos dadosOrdem, Orcamento orcamento) {
        return new OrdemServicoBuilder()
                .dados(dadosOrdem)
                .id(null)
                .orcamento(orcamento)
                .status(ABERTA)
                .dataAbertura(null)
                .dataConclusao(null)
                .build();
    }

    static FichaTecnica novaFicha(Campos dadosFicha, OrdemServico ordem) {
        return new FichaTecnicaBuilder()
                .dados(dadosFicha)
                .id(null)
                .ordemServico(ordem)
                .dataRegistro(null)
                .build();
    }
}
