package com.assistencia.model.valor;

import com.assistencia.dados.*;

/** Texto livre registrado em um momento (ex.: descrição + data de cadastro). */
public final class Anotacao implements Exportavel {
    private final Texto texto;
    private final DataHora momento;

    public Anotacao(Texto texto, DataHora momento) {
        this.texto = texto;
        this.momento = momento;
    }

    @Override
    public void exportar(Campos destino) {
        texto.exportar(destino);
        momento.exportar(destino);
    }
}
