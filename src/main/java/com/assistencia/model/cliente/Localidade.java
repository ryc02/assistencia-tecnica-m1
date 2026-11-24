package com.assistencia.model.cliente;

import com.assistencia.dados.*;
import com.assistencia.model.valor.Texto;

/** Bairro e cidade do endereço. */
public final class Localidade implements Exportavel {
    private final Texto bairro;
    private final Texto cidade;

    public Localidade(Texto bairro, Texto cidade) {
        this.bairro = bairro;
        this.cidade = cidade;
    }

    public static Localidade de(Campos dados) {
        Texto bairro = Texto.obrigatorio(dados, "bairro", 80, "Bairro");
        Texto cidade = Texto.obrigatorio(dados, "cidade", 80, "Cidade");
        return new Localidade(bairro, cidade);
    }

    @Override
    public void exportar(Campos destino) {
        bairro.exportar(destino);
        cidade.exportar(destino);
    }
}
