package com.assistencia.model.cliente;

import com.assistencia.dados.*;
import com.assistencia.model.valor.DataHora;

/** Endereço e data de cadastro do cliente. */
public final class Residencia implements Exportavel {
    private final Endereco endereco;
    private final DataHora dataCadastro;

    public Residencia(Endereco endereco, DataHora dataCadastro) {
        this.endereco = endereco;
        this.dataCadastro = dataCadastro;
    }

    public static Residencia de(Campos dados) {
        Endereco endereco = Endereco.de(dados);
        DataHora dataCadastro = DataHora.agoraSeVazio(dados, "dataCadastro");
        return new Residencia(endereco, dataCadastro);
    }

    @Override
    public void exportar(Campos destino) {
        endereco.exportar(destino);
        dataCadastro.exportar(destino);
    }
}
