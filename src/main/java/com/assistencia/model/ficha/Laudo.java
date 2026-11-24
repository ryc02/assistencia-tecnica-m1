package com.assistencia.model.ficha;

import com.assistencia.dados.*;

/** Condição do equipamento e dados do recebimento. */
public final class Laudo implements Exportavel {
    private final Condicao condicao;
    private final Recebimento recebimento;

    public Laudo(Condicao condicao, Recebimento recebimento) {
        this.condicao = condicao;
        this.recebimento = recebimento;
    }

    public static Laudo de(Campos dados) {
        Condicao condicao = Condicao.de(dados);
        Recebimento recebimento = Recebimento.de(dados);
        return new Laudo(condicao, recebimento);
    }

    @Override
    public void exportar(Campos destino) {
        condicao.exportar(destino);
        recebimento.exportar(destino);
    }
}
