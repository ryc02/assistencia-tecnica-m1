package com.assistencia.model.cliente;

import com.assistencia.dados.*;

/** Identidade e contato do cliente. */
public final class DadosPessoais implements Exportavel {
    private final Identidade identidade;
    private final Contato contato;

    public DadosPessoais(Identidade identidade, Contato contato) {
        this.identidade = identidade;
        this.contato = contato;
    }

    public static DadosPessoais de(Campos dados) {
        Identidade identidade = Identidade.de(dados);
        Contato contato = Contato.de(dados);
        return new DadosPessoais(identidade, contato);
    }

    @Override
    public void exportar(Campos destino) {
        identidade.exportar(destino);
        contato.exportar(destino);
    }
}
