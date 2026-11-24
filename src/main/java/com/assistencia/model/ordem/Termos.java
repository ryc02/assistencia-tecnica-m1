package com.assistencia.model.ordem;

import com.assistencia.dados.*;
import com.assistencia.model.valor.*;

/** Observações e prazo de garantia do serviço. */
public final class Termos implements Exportavel {
    private final Texto observacoes;
    private final PrazoGarantia prazo;

    public Termos(Texto observacoes, PrazoGarantia prazo) {
        this.observacoes = observacoes;
        this.prazo = prazo;
    }

    public static Termos de(Campos dados) {
        Texto observacoes = Texto.opcional(dados, "observacoes", 2000, "Observações");
        PrazoGarantia prazo = PrazoGarantia.de(dados);
        return new Termos(observacoes, prazo);
    }

    @Override
    public void exportar(Campos destino) {
        observacoes.exportar(destino);
        prazo.exportar(destino);
    }
}
