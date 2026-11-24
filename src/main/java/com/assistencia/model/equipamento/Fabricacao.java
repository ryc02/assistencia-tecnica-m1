package com.assistencia.model.equipamento;

import com.assistencia.dados.*;

/** Categoria e fabricante do equipamento. */
public final class Fabricacao implements Exportavel {
    private final Categoria categoria;
    private final Fabricante fabricante;

    public Fabricacao(Categoria categoria, Fabricante fabricante) {
        this.categoria = categoria;
        this.fabricante = fabricante;
    }

    public static Fabricacao de(Campos dados) {
        Categoria categoria = Categoria.de(dados);
        Fabricante fabricante = Fabricante.de(dados);
        return new Fabricacao(categoria, fabricante);
    }

    @Override
    public void exportar(Campos destino) {
        categoria.exportar(destino);
        fabricante.exportar(destino);
    }
}
