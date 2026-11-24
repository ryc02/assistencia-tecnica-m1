package com.assistencia.model.ficha;

import com.assistencia.dados.*;
import com.assistencia.exception.ValidationException;
import com.assistencia.model.valor.*;

/** Avarias visíveis: a descrição é obrigatória quando possuiAvarias = true. */
public final class Avarias implements Exportavel {
    private final Verificacao possuiAvarias;
    private final Texto descricao;

    private Avarias(Verificacao possuiAvarias, Texto descricao) {
        this.possuiAvarias = possuiAvarias;
        this.descricao = descricao;
    }

    public static Avarias de(Campos dados) {
        Verificacao possui = Verificacao.de(dados, "possuiAvarias");
        Texto descricao = Texto.opcional(dados, "descricaoAvarias", 2000, "Descrição das avarias");
        boolean confirmada = possui.confirmada();
        if (confirmada && descricao.emBranco()) {
            throw new ValidationException("Descrição das avarias é obrigatória quando possuiAvarias = true.");
        }
        return new Avarias(possui, descricao);
    }

    @Override
    public void exportar(Campos destino) {
        possuiAvarias.exportar(destino);
        descricao.exportar(destino);
    }
}
