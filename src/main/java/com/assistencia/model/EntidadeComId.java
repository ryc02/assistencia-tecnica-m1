package com.assistencia.model;

import com.assistencia.dados.*;
import com.assistencia.model.valor.Identificador;

/** Base das entidades: guarda o identificador e exporta id + dados (herança com classe abstrata). */
public abstract class EntidadeComId implements Entidade {
    private final Identificador identificador;

    protected EntidadeComId(Identificador identificador) {
        this.identificador = identificador;
    }

    @Override
    public boolean possuiId(Long id) {
        return identificador.igualA(id);
    }

    @Override
    public Campos exportar() {
        Campos destino = new Campos();
        identificador.exportar(destino);
        exportarDados(destino);
        return destino;
    }

    protected abstract void exportarDados(Campos destino);
}
