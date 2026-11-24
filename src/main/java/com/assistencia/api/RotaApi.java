package com.assistencia.api;

import com.assistencia.dados.Campos;

/** Rota da requisição REST: método HTTP + caminho (recurso, id e ação). */
final class RotaApi {
    private final MetodoHttp metodo;
    private final CaminhoApi caminho;

    RotaApi(String metodo, CaminhoApi caminho) {
        this.metodo = new MetodoHttp(metodo);
        this.caminho = caminho;
    }

    boolean eh(String metodoEsperado, String recursoEsperado) {
        boolean mesmoMetodo = metodo.eh(metodoEsperado);
        return mesmoMetodo && caminho.eh(recursoEsperado);
    }

    boolean alteraDados() {
        return metodo.alteraDados();
    }

    RespostaRest executar(Campos corpo) throws Exception {
        return caminho.executar(metodo, corpo);
    }
}
