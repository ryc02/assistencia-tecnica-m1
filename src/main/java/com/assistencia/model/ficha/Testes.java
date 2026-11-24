package com.assistencia.model.ficha;

import com.assistencia.dados.*;
import com.assistencia.model.valor.*;

/** Resultado dos testes iniciais na recepção. */
public final class Testes implements Exportavel {
    private final Verificacao ligaNormalmente;
    private final Texto testeInicial;

    public Testes(Verificacao ligaNormalmente, Texto testeInicial) {
        this.ligaNormalmente = ligaNormalmente;
        this.testeInicial = testeInicial;
    }

    public static Testes de(Campos dados) {
        Verificacao ligaNormalmente = Verificacao.de(dados, "ligaNormalmente");
        Texto testeInicial = Texto.comPadrao(dados, "testeInicial", 2000, "Teste inicial", "Não realizado");
        return new Testes(ligaNormalmente, testeInicial);
    }

    @Override
    public void exportar(Campos destino) {
        ligaNormalmente.exportar(destino);
        testeInicial.exportar(destino);
    }
}
