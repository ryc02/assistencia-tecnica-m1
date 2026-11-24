package com.assistencia.model.equipamento;

import com.assistencia.dados.*;
import com.assistencia.model.valor.Texto;

/** Marca e modelo do equipamento. */
public final class Fabricante implements Exportavel {
    private final Texto marca;
    private final Texto modelo;

    public Fabricante(Texto marca, Texto modelo) {
        this.marca = marca;
        this.modelo = modelo;
    }

    public static Fabricante de(Campos dados) {
        Texto marca = Texto.obrigatorio(dados, "marca", 60, "Marca");
        Texto modelo = Texto.obrigatorio(dados, "modelo", 80, "Modelo");
        return new Fabricante(marca, modelo);
    }

    @Override
    public void exportar(Campos destino) {
        marca.exportar(destino);
        modelo.exportar(destino);
    }
}
