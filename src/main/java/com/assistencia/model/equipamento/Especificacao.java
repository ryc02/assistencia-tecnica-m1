package com.assistencia.model.equipamento;

import com.assistencia.dados.*;

/** Fabricação e detalhes do equipamento. */
public final class Especificacao implements Exportavel {
    private final Fabricacao fabricacao;
    private final Detalhes detalhes;

    public Especificacao(Fabricacao fabricacao, Detalhes detalhes) {
        this.fabricacao = fabricacao;
        this.detalhes = detalhes;
    }

    public static Especificacao de(Campos dados) {
        Fabricacao fabricacao = Fabricacao.de(dados);
        Detalhes detalhes = Detalhes.de(dados);
        return new Especificacao(fabricacao, detalhes);
    }

    @Override
    public void exportar(Campos destino) {
        fabricacao.exportar(destino);
        detalhes.exportar(destino);
    }
}
