package com.assistencia.model.cliente;

import com.assistencia.dados.*;
import com.assistencia.model.valor.Texto;

/** Logradouro e número do endereço. */
public final class LinhaEndereco implements Exportavel {
    private final Texto logradouro;
    private final Texto numero;

    public LinhaEndereco(Texto logradouro, Texto numero) {
        this.logradouro = logradouro;
        this.numero = numero;
    }

    public static LinhaEndereco de(Campos dados) {
        Texto logradouro = Texto.obrigatorio(dados, "logradouro", 150, "Logradouro");
        Texto numero = Texto.obrigatorio(dados, "numero", 20, "Número");
        return new LinhaEndereco(logradouro, numero);
    }

    @Override
    public void exportar(Campos destino) {
        logradouro.exportar(destino);
        numero.exportar(destino);
    }
}
