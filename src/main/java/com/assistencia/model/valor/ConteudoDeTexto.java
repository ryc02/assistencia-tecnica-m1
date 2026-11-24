package com.assistencia.model.valor;

import com.assistencia.dados.Campos;

/** Conteúdo de um campo de texto (já validado e sem espaços nas pontas); vazio quando não informado. */
final class ConteudoDeTexto {
    private final String valor;

    ConteudoDeTexto(String valor) {
        this.valor = valor;
    }

    boolean emBranco() {
        return valor == null;
    }

    void gravarEm(Chave chave, Campos destino) {
        chave.gravar(destino, valor);
    }
}
