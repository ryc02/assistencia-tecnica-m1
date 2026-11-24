package com.assistencia.model.equipamento;

import com.assistencia.dados.*;
import com.assistencia.model.valor.*;

/** Tipo e voltagem do equipamento. */
public final class Categoria implements Exportavel {
    private final Texto tipo;
    private final Voltagem voltagem;

    public Categoria(Texto tipo, Voltagem voltagem) {
        this.tipo = tipo;
        this.voltagem = voltagem;
    }

    public static Categoria de(Campos dados) {
        Texto tipo = Texto.obrigatorio(dados, "tipo", 60, "Tipo");
        Voltagem voltagem = Voltagem.de(dados);
        return new Categoria(tipo, voltagem);
    }

    @Override
    public void exportar(Campos destino) {
        tipo.exportar(destino);
        voltagem.exportar(destino);
    }
}
