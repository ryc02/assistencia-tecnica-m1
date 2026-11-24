package com.assistencia.decorator;

import com.assistencia.dados.Campos;

/** Descrição de um adicional, anexada à do serviço decorado (ex.: "Serviço técnico, Backup de dados"). */
final class Descricao {
    private final String texto;

    Descricao(String texto) {
        this.texto = texto;
    }

    String anexarA(String descricaoAnterior) {
        return descricaoAnterior + ", " + texto;
    }

    void gravarEm(Campos destino) {
        destino.com("descricao", texto);
    }
}
