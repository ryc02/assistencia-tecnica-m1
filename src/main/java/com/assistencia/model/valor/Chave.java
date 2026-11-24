package com.assistencia.model.valor;

import com.assistencia.dados.Campos;

/** Nome do campo em que um valor é exportado (ex.: "descricaoProblema", "dataCriacao"). */
public final class Chave {
    private final String nome;

    public Chave(String nome) {
        this.nome = nome;
    }

    public void gravar(Campos destino, Object valor) {
        destino.com(nome, valor);
    }

    public Chave comSufixo(String sufixo) {
        return new Chave(nome + sufixo);
    }
}
