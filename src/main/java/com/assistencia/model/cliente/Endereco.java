package com.assistencia.model.cliente;

import com.assistencia.dados.*;

/** Endereço completo do cliente. */
public final class Endereco implements Exportavel {
    private final LinhaEndereco linha;
    private final Localidade localidade;

    public Endereco(LinhaEndereco linha, Localidade localidade) {
        this.linha = linha;
        this.localidade = localidade;
    }

    public static Endereco de(Campos dados) {
        LinhaEndereco linha = LinhaEndereco.de(dados);
        Localidade localidade = Localidade.de(dados);
        return new Endereco(linha, localidade);
    }

    @Override
    public void exportar(Campos destino) {
        linha.exportar(destino);
        localidade.exportar(destino);
    }
}
