package com.assistencia.model.valor;

import com.assistencia.dados.*;

/** Resultado de uma verificação: sim, não ou não verificado (nulo). */
public final class Verificacao implements Exportavel {
    private final Chave chave;
    private final Resposta resposta;

    private Verificacao(String chave, Boolean valor) {
        this.chave = new Chave(chave);
        this.resposta = new Resposta(valor);
    }

    public static Verificacao de(Campos dados, String chave) {
        return new Verificacao(chave, dados.logico(chave));
    }

    public boolean confirmada() {
        return resposta.afirmativa();
    }

    @Override
    public void exportar(Campos destino) {
        resposta.gravarEm(chave, destino);
    }
}
