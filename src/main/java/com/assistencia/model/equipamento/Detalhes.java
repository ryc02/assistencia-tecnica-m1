package com.assistencia.model.equipamento;

import com.assistencia.dados.*;
import com.assistencia.model.valor.*;

/** Aparência, descrição e data de cadastro do equipamento. */
public final class Detalhes implements Exportavel {
    private final Aparencia aparencia;
    private final Anotacao anotacao;

    public Detalhes(Aparencia aparencia, Anotacao anotacao) {
        this.aparencia = aparencia;
        this.anotacao = anotacao;
    }

    public static Detalhes de(Campos dados) {
        Aparencia aparencia = Aparencia.de(dados);
        Anotacao anotacao = anotacaoDe(dados);
        return new Detalhes(aparencia, anotacao);
    }

    @Override
    public void exportar(Campos destino) {
        aparencia.exportar(destino);
        anotacao.exportar(destino);
    }

    private static Anotacao anotacaoDe(Campos dados) {
        Texto descricao = Texto.opcional(dados, "descricao", 1000, "Descrição");
        return new Anotacao(descricao, DataHora.agoraSeVazio(dados, "dataCadastro"));
    }
}
