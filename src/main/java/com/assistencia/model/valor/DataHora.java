package com.assistencia.model.valor;

import com.assistencia.dados.*;
import java.time.LocalDateTime;

/** Data e hora com o nome do campo que representa (ex.: dataCadastro, previsaoConclusao). */
public final class DataHora implements Exportavel {
    private final Chave chave;
    private final Instante instante;

    private DataHora(String chave, LocalDateTime valor) {
        this.chave = new Chave(chave);
        this.instante = new Instante(valor);
    }

    public static DataHora de(Campos dados, String chave) {
        return new DataHora(chave, dados.dataHora(chave));
    }

    public static DataHora agora(String chave) {
        return new DataHora(chave, LocalDateTime.now());
    }

    public static DataHora agoraSeVazio(Campos dados, String chave) {
        DataHora informada = de(dados, chave);
        if (informada.vazia()) {
            return agora(chave);
        }
        return informada;
    }

    public void exigirPosteriorA(DataHora referencia, String mensagem) {
        Instante limite = referencia.instante;
        instante.exigirPosteriorA(limite, mensagem);
    }

    @Override
    public void exportar(Campos destino) {
        instante.gravarEm(chave, destino);
    }

    private boolean vazia() {
        return instante.vazio();
    }
}
