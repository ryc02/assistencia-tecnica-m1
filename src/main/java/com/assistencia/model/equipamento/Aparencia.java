package com.assistencia.model.equipamento;

import com.assistencia.dados.*;
import com.assistencia.model.valor.Texto;

/** Número de série e cor (opcionais). */
public final class Aparencia implements Exportavel {
    private final Texto numeroSerie;
    private final Texto cor;

    public Aparencia(Texto numeroSerie, Texto cor) {
        this.numeroSerie = numeroSerie;
        this.cor = cor;
    }

    public static Aparencia de(Campos dados) {
        Texto numeroSerie = Texto.opcional(dados, "numeroSerie", 100, "Número de série");
        Texto cor = Texto.opcional(dados, "cor", 40, "Cor");
        return new Aparencia(numeroSerie, cor);
    }

    @Override
    public void exportar(Campos destino) {
        numeroSerie.exportar(destino);
        cor.exportar(destino);
    }
}
