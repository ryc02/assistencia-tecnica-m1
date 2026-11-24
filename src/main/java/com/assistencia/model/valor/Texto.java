package com.assistencia.model.valor;

import com.assistencia.dados.*;
import com.assistencia.exception.ValidationException;

/** [Calistenia - primitivos encapsulados] Texto descritivo com nome do campo e limite de tamanho. */
public final class Texto implements Exportavel {
    private final Chave chave;
    private final ConteudoDeTexto conteudo;

    private Texto(String chave, String valor) {
        this.chave = new Chave(chave);
        this.conteudo = new ConteudoDeTexto(valor);
    }

    public static Texto obrigatorio(Campos dados, String chave, int limite, String rotulo) {
        if (dados.texto(chave) == null) {
            throw new ValidationException(rotulo + " é obrigatório.");
        }
        return opcional(dados, chave, limite, rotulo);
    }

    public static Texto opcional(Campos dados, String chave, int limite, String rotulo) {
        String valor = dados.texto(chave);
        LimiteDeTamanho.exigir(valor, limite, rotulo);
        return new Texto(chave, valor);
    }

    public static Texto comPadrao(Campos dados, String chave, int limite, String rotulo, String padrao) {
        Texto informado = opcional(dados, chave, limite, rotulo);
        if (informado.emBranco()) {
            return new Texto(chave, padrao);
        }
        return informado;
    }

    public boolean emBranco() {
        return conteudo.emBranco();
    }

    @Override
    public void exportar(Campos destino) {
        conteudo.gravarEm(chave, destino);
    }
}
