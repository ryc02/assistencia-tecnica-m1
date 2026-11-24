package com.assistencia.model.ficha;

import com.assistencia.dados.*;

/** Testes iniciais e dados da entrega do equipamento. */
public final class Recebimento implements Exportavel {
    private final Testes testes;
    private final Entrega entrega;

    public Recebimento(Testes testes, Entrega entrega) {
        this.testes = testes;
        this.entrega = entrega;
    }

    public static Recebimento de(Campos dados) {
        Testes testes = Testes.de(dados);
        Entrega entrega = Entrega.de(dados);
        return new Recebimento(testes, entrega);
    }

    @Override
    public void exportar(Campos destino) {
        testes.exportar(destino);
        entrega.exportar(destino);
    }
}
